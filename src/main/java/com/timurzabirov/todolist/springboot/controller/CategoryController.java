package com.timurzabirov.todolist.springboot.controller;

import com.timurzabirov.todolist.springboot.repository.CategoryRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/*@RestController вместо @Controller для того, чтобы ответы были сразу в виде JSON.
* Иначе надо было бы использовать @ResponseBody для ответа*/
@RestController
@RequestMapping("/category") // Базовый адрес для всех методов класса
public class CategoryController {

    //Доступ к данным из БД
    private final CategoryRepository categoryRepository;

    /*Внедрение экземпляра класса через конструктор.
     * Не используем @Autowired для переменной,т.к. Field injection is not recommended*/
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
}
