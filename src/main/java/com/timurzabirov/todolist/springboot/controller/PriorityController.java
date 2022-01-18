package com.timurzabirov.todolist.springboot.controller;

import com.timurzabirov.todolist.springboot.entity.Priority;
import com.timurzabirov.todolist.springboot.repository.PriorityRepository;
import com.timurzabirov.todolist.springboot.search.PrioritySearchValues;
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
    private final PriorityRepository priorityRepository;

    /*Внедрение экземпляра класса через конструктор.
     * Не используем @Autowired для переменной,т.к. Field injection is not recommended*/
    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
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
        // Метод save() используется как для добавления, так и для обновления сущности
        return ResponseEntity.ok(priorityRepository.save(priority));
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
        // Метод save() используется как для добавления, так и для обновления сущности
        priorityRepository.save(priority);
        return new ResponseEntity(HttpStatus.OK);
    }

    //Параметр id передается не в теле запроса, а в URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Priority> getById(@PathVariable Long id) {
        Priority priority = null;
        try {
            priority = priorityRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(priority);
    }

    @GetMapping("/all")
    public List<Priority> findAll() {
        return priorityRepository.findAllByOrderByIdAsc();
    }

    //Параметр id передается не в теле запроса, а в URL
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        try {
            priorityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity("Id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    // Поиск по любым параметрам(CategorySearchValues class)
    @PostMapping("/search")
    public ResponseEntity<List<Priority>> search(@RequestBody PrioritySearchValues prioritySearchValues) {
        // Если вместо текста будет null или пустота, то вернутся все значения
        return ResponseEntity.ok(priorityRepository.findByTitle(prioritySearchValues.getText()));
    }

}
