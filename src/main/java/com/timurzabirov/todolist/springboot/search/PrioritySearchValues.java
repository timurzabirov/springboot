package com.timurzabirov.todolist.springboot.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
// Возможные значение, по которым можно искать категории
public class PrioritySearchValues {

    private String text;
}
