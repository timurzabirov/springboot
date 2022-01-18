package com.timurzabirov.todolist.springboot.repository;

import com.timurzabirov.todolist.springboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Абстракция и реализация (Описываются способы доступа к данным в БД)
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Получение всех значений, сортированных по названию
    List<Category> findAllByOrderByTitleAsc();

    //Если title == null или == '', то получим все значения
    @Query("SELECT c FROM Category c where " +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title, '%'))) " +
            "order by c.title asc")
    List<Category> findByTitle(@Param("title") String title);
}
