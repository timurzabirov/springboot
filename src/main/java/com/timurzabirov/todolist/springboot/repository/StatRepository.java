package com.timurzabirov.todolist.springboot.repository;

import com.timurzabirov.todolist.springboot.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Абстракция и реализация (Описываются способы доступа к данным в БД)
@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {

}
