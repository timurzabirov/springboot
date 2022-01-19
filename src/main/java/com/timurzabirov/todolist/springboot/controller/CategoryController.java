package com.timurzabirov.todolist.springboot.controller;

import com.timurzabirov.todolist.springboot.entity.Category;
import com.timurzabirov.todolist.springboot.search.CategorySearchValues;
import com.timurzabirov.todolist.springboot.service.CategoryService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/*@RestController вместо @Controller для того, чтобы ответы были сразу в виде JSON.
 * Иначе надо было бы использовать @ResponseBody для ответа*/
@RestController
@RequestMapping("/category") // Базовый адрес для всех методов класса
public class CategoryController {

    //Доступ к данным из БД
    private final CategoryService service;

    /*Внедрение экземпляра класса через конструктор.
     * Не используем @Autowired для переменной,т.к. Field injection is not recommended*/
    public CategoryController(CategoryService service) {
        this.service = service;
    }


    @PostMapping("/add")
    public ResponseEntity<Category> add(@RequestBody Category category) {

        // Проверка на обязательные параметры
        if (category.getId() != null && category.getId() != 0) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null && category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // ResponseEntity - это объект-коробка, в который мы можем поместить объект и статус запроса
        // Метод save() используется как для добавления, так и для обновления сущности
        return ResponseEntity.ok(service.add(category));
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {

        // Проверка на обязательные параметры
        if (category.getId() == null && category.getId() == 0) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null && category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // ResponseEntity - это объект-коробка, в который мы можем поместить объект и статус запроса
        // Метод save() используется как для добавления, так и для обновления сущности
        service.add(category);
        return new ResponseEntity(HttpStatus.OK);
    }

    //Параметр id передается не в теле запроса, а в URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Category category = null;
        try {
            category = service.findById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(category);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(service.findAllByOrderByTitleAsc());
    }

    //Параметр id передается не в теле запроса, а в URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity("Id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    // Поиск по любым параметрам(CategorySearchValues class)
    @PostMapping("/search")
    public ResponseEntity<List<Category>> search(@RequestBody CategorySearchValues categorySearchValues) {
        // Если вместо текста будет null или пустота, то вернутся все значения
        return ResponseEntity.ok(service.findByTitle(categorySearchValues.getText()));
    }

}
