package com.timurzabirov.todolist.springboot.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class Priority {

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
    @Column(name = "color")
    private String color;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public String getColor() {
        return color;
    }

}
