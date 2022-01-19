package com.timurzabirov.todolist.springboot.controller;

import com.timurzabirov.todolist.springboot.entity.Priority;
import com.timurzabirov.todolist.springboot.search.PrioritySearchValues;
import com.timurzabirov.todolist.springboot.service.PriorityService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/*@RestController вместо @Controller для того, чтобы ответы были сразу в виде JSON.
 * Иначе надо было бы использовать @ResponseBody для ответа*/
@RestController
@RequestMapping("/priority") // Базовый адрес для всех методов класса
public class PriorityController {

    //Доступ к данным из БД
    private final PriorityService service;

    /*Внедрение экземпляра класса через конструктор.
     * Не используем @Autowired для переменной,т.к. Field injection is not recommended*/
    public PriorityController(PriorityService service) {
        this.service = service;
    }

    @PostMapping("/add")
    public ResponseEntity<Priority> add(@RequestBody Priority priority) {
        // Проверка на обязательные параметры
        if (priority.getId() != null && priority.getId() != 0) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null && priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null && priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // ResponseEntity - это объект-коробка, в который мы можем поместить объект и статус запроса
        return ResponseEntity.ok(service.add(priority));
    }

    @PutMapping("/update")
    public ResponseEntity<Priority> update(@RequestBody Priority priority) {
        // Проверка на обязательные параметры
        if (priority.getId() == null && priority.getId() == 0) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null && priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null && priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // ResponseEntity - это объект-коробка, в который мы можем поместить объект и статус запроса
        service.add(priority);
        return new ResponseEntity(HttpStatus.OK);
    }

    //Параметр id передается не в теле запроса, а в URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> getById(@PathVariable Long id) {
        Priority priority = null;
        try {
            priority = service.findById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }

    @GetMapping("/all")
    public List<Priority> findAll() {
        return service.findAll();
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
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {
        // Если вместо текста будет null или пустота, то вернутся все значения
        return ResponseEntity.ok(service.findByTitle(prioritySearchValues.getText()));
    }

}
