package com.todoapi.task.service;

import com.todoapi.task.entity.Task;

public interface EmailService {

    void sendTaskCreationEmail(Task task);
}
