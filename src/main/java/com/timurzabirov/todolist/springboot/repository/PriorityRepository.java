package com.timurzabirov.todolist.springboot.repository;

import com.timurzabirov.todolist.springboot.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Абстракция и реализация (Описываются способы доступа к данным в БД)
@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    // Получение всех значений, сортированных по id
    List<Priority> findAllByOrderByIdAsc();

    //Если title == null или == '', то получим все значения
    @Query("SELECT p FROM Priority p where " +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title, '%'))) " +
            "order by p.title asc")
    List<Priority> findByTitle(@Param("title") String title);
}
