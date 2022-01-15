package com.timurzabirov.todolist.springboot.controller;

import com.timurzabirov.todolist.springboot.repository.PriorityRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
