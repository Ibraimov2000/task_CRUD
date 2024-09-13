package com.todoapi.task.service;

import com.todoapi.task.entity.Task;
import com.todoapi.task.mapper.TaskMapper;
import com.todoapi.task.model.TaskRecord;
import com.todoapi.task.repository.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final EmailService emailService;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    public TaskServiceImpl(TaskRepository taskRepository, EmailService emailService) {
        this.taskRepository = taskRepository;
        this.emailService = emailService;
    }

    @Override
    public TaskRecord getTaskById(Long id) {
        log.info("Fetching task with ID: {}", id);
        TaskRecord taskRecord = taskRepository.findById(id)
                .map(taskMapper::toTaskRecord)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));
        log.info("Task fetched successfully: {}", taskRecord);
        return taskRecord;
    }

    @Cacheable("tasks")
    @Override
    public List<TaskRecord> getAllTasks() {
        log.info("Fetching all tasks");
        List<TaskRecord> tasks = taskRepository.findAll().stream()
                .map(taskMapper::toTaskRecord)
                .collect(Collectors.toList());
        log.info("Fetched {} tasks", tasks.size());
        return tasks;
    }

    @Override
    public TaskRecord addTask(TaskRecord taskRecord) {
        log.info("Adding new task: {}", taskRecord);
        Task task = taskMapper.toTask(taskRecord);
        Task savedTask = taskRepository.save(task);
        log.info("Task added successfully: {}", savedTask);

        emailService.sendTaskCreationEmail(savedTask);
        return taskMapper.toTaskRecord(savedTask);
    }

    @Override
    public TaskRecord updateTask(Long id, TaskRecord updatedTask) {
        log.info("Updating task with ID: {}", id);
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task with ID " + id + " not found"));

        task.setDescription(updatedTask.description());
        task.setCompleted(updatedTask.completed());
        Task savedTask = taskRepository.save(task);
        log.info("Task updated successfully: {}", savedTask);
        return taskMapper.toTaskRecord(savedTask);
    }

    @Override
    public void deleteTask(Long id) {
        log.info("Deleting task with ID: {}", id);
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task with ID " + id + " not found");
        }
        taskRepository.deleteById(id);
        log.info("Task with ID {} deleted successfully", id);
    }
}
