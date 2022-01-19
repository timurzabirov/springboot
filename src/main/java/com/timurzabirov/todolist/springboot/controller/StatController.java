package com.timurzabirov.todolist.springboot.controller;

import com.timurzabirov.todolist.springboot.entity.Stat;
import com.timurzabirov.todolist.springboot.service.StatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*@RestController вместо @Controller для того, чтобы ответы были сразу в виде JSON.
 * Иначе надо было бы использовать @ResponseBody для ответа*/
@RestController
@RequestMapping("/stat/") // Базовый адрес для всех методов класса
public class StatController {

    //Сервис для доступа к данным из БД
    private final StatService service;

    /*Внедрение экземпляра класса через конструктор.
     * Не используем @Autowired для переменной,т.к. Field injection is not recommended*/
    public StatController(StatService statService) {
        this.service = statService;
    }

    @GetMapping("/get")
    public ResponseEntity<Stat> getStat() {
        return ResponseEntity.ok(service.findById(1L));
    }
}
