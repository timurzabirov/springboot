package com.timurzabirov.todolist.springboot.controller;

import com.timurzabirov.todolist.springboot.entity.Task;
import com.timurzabirov.todolist.springboot.search.TaskSearchValues;
import com.timurzabirov.todolist.springboot.service.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/*@RestController вместо @Controller для того, чтобы ответы были сразу в виде JSON.
 * Иначе надо было бы использовать @ResponseBody для ответа*/
@RestController
@RequestMapping("/task") // Базовый адрес для всех методов класса
public class TaskController {

    //Доступ к данным из БД
    private final TaskService service;

    /*Внедрение экземпляра класса через конструктор.
     * Не используем @Autowired для переменной,т.к. Field injection is not recommended*/
    public TaskController(TaskService taskService) {
        this.service = taskService;
    }

    //Получение всех данных
    @GetMapping("/all")
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    //Добавление сущности
    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {

        // Проверка на обязательные параметры
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null && task.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // ResponseEntity - это объект-коробка, в который мы можем поместить объект и статус запроса
        return ResponseEntity.ok(service.add(task));
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {

        // Проверка на обязательные параметры
        if (task.getId() == null && task.getId() == 0) {
            return new ResponseEntity("Redundant param: id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null && task.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        // ResponseEntity - это объект-коробка, в который мы можем поместить объект и статус запроса
        service.add(task);
        return new ResponseEntity(HttpStatus.OK);
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

    //Параметр id передается не в теле запроса, а в URL
    @GetMapping("/id/{id}")
    public ResponseEntity<Task> getById(@PathVariable Long id) {
        Task task = null;
        try {
            task = service.findById(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity("Id = " + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> searchByParams(@RequestBody TaskSearchValues taskSearchValues) {

        //Исключаем NPE
        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;

        //Конвертируем boolean в Integer
        Integer completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;

        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Sort.Direction direction = sortDirection == null || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        //Объект сортировки
        Sort sort = Sort.by(direction, sortColumn);

        // Объект постраничности
        PageRequest pageRequest = PageRequest.of(taskSearchValues.getPageNumber(), taskSearchValues.getPageSize());

        //Результат запроса с постраничным выводом
        Page result = service.findByParams(title, completed, priorityId, categoryId, pageRequest);

        //Результат запроса
        return ResponseEntity.ok(result);
    }
}
