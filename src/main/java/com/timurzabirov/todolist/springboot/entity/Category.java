package com.timurzabirov.todolist.springboot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class Category {

    /*Указываем, что поле заполняется в БД
     * При добавлении нового объекта ему присваивается новый id */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "title")
    private String title;
    @Basic
    @Column(name = "completed_count")
    private Long completedCount;
    @Basic
    @Column(name = "uncompleted_count")
    private Long uncompletedCount;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Long getCompletedCount() {
        return completedCount;
    }

    public Long getUncompletedCount() {
        return uncompletedCount;
    }


}
