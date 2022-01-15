package com.timurzabirov.todolist.springboot.repository;

import com.timurzabirov.todolist.springboot.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Абстракция и реализация (Описываются способы доступа к данным в БД)
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
