package com.timurzabirov.todolist.springboot.service;

import com.timurzabirov.todolist.springboot.entity.Stat;
import com.timurzabirov.todolist.springboot.repository.StatRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
// Все методы должны отработать без ошибки, чтобы транзакция завершилась, иначе все операции откатятся(Rollback)
public class StatService {

    private final StatRepository repository; // сервис имеет право обращаться к репозиторию (БД)

    public StatService(StatRepository repository) {
        this.repository = repository;
    }

    public Stat findById(Long id) {
        return repository.findById(id).get();
    }


}
