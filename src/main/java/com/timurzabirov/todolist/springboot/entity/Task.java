package com.timurzabirov.todolist.springboot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class Task {

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
    @Column(name = "completed")
    private Integer completed;

    @Basic
    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCompleted() {
        return completed;
    }

    public Date getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }

    public Category getCategory() {
        return category;
    }

}
