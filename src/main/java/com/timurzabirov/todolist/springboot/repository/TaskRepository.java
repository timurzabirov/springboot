package com.timurzabirov.todolist.springboot.repository;

import com.timurzabirov.todolist.springboot.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Абстракция и реализация (Описываются способы доступа к данным в БД)
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Параметр может быть пустым или null
    @Query("SELECT t FROM Task t where " +
            "(:title is null or :title='' or lower(t.title) like lower(concat('%', :title, '%'))) and" +
            "(:completed is null or t.completed=:completed) and " +
            "(:priorityId is null or t.priority.id=:priorityId) and " +
            "(:categoryId is null or t.category.id=:categoryId)")
    //Поиск по всем переданным параметрам(пустые параметры не будут учитываться(is null))
    List<Task> findByParams(@Param("title") String title,
                            @Param("completed") Integer completed,
                            @Param("priorityId") Long priorityId,
                            @Param("categoryId") Long categoryId);
}
