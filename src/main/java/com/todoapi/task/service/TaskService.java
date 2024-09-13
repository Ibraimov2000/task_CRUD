package com.todoapi.task.service;

import com.todoapi.task.model.TaskRecord;

import java.util.List;

public interface TaskService {

    TaskRecord getTaskById(Long id);
    List<TaskRecord> getAllTasks();

    TaskRecord addTask(TaskRecord taskRecord);

    TaskRecord updateTask(Long id, TaskRecord updatedTask);

    void deleteTask(Long id);
}
