package com.todoapi.task.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column
    String description;
    @Column
    boolean completed;
}
