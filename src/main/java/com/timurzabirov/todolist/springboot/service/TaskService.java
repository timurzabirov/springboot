package com.timurzabirov.todolist.springboot.service;

import com.timurzabirov.todolist.springboot.entity.Task;
import com.timurzabirov.todolist.springboot.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
// Все методы должны отработать без ошибки, чтобы транзакция завершилась, иначе все операции откатятся(Rollback)
public class TaskService {

    private final TaskRepository repository; // сервис имеет право обращаться к репозиторию (БД)

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    public List<Task> findAll() {
        return repository.findAll();
    }

    public Task add(Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public Task update(Task task) {
        return repository.save(task); // метод save обновляет или создает новый объект, если его не было
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public Page findByParams(String text, Integer completed, Long priorityId, Long categoryId, PageRequest paging) {
        return repository.findByParams(text, completed, priorityId, categoryId, paging);
    }

    public Task findById(Long id) {
        return repository.findById(id).get(); // т.к. возвращается Optional - нужно получить объект методом get()
    }


}

