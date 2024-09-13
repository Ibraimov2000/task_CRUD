package com.todoapi.task.service;

import com.todoapi.task.entity.Task;
import com.todoapi.task.mapper.TaskMapper;
import com.todoapi.task.model.TaskRecord;
import com.todoapi.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private EmailService emailService;

    private final TaskMapper taskMapper = TaskMapper.INSTANCE;
    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setDescription("Test Task");
        task.setCompleted(false);
    }

    @Test
    void shouldGetTaskById() {
        Mockito.when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        TaskRecord taskRecord = taskService.getTaskById(1L);

        assertNotNull(taskRecord);
        assertEquals("Test Task", taskRecord.description());
        assertFalse(taskRecord.completed());
    }


    @Test
    void shouldAddTask() {
        TaskRecord taskRecord = new TaskRecord(null, "New Task", false);
        Task taskToSave = taskMapper.toTask(taskRecord);
        Mockito.when(taskRepository.save(taskToSave))
                .thenReturn(taskToSave);
        Mockito.doNothing().when(emailService).sendTaskCreationEmail(taskToSave);

        TaskRecord createdTaskRecord = taskService.addTask(taskRecord);

        assertNotNull(createdTaskRecord);
        assertEquals("New Task", createdTaskRecord.description());
    }

    @Test
    void shouldUpdateTask() {
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setDescription("Updated Task");
        updatedTask.setCompleted(true);

        Mockito.when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(Mockito.any(Task.class)))
                .thenReturn(updatedTask);

        TaskRecord updatedTaskRecord = new TaskRecord(1L, "Updated Task", true);
        TaskRecord result = taskService.updateTask(1L, updatedTaskRecord);

        assertEquals("Updated Task", result.description());
        assertTrue(result.completed());
    }

    @Test
    void shouldDeleteTask() {
        Mockito.doNothing().when(taskRepository).deleteById(1L);
        Mockito.when(taskRepository.existsById(1L)).thenReturn(true);

        taskService.deleteTask(1L);

        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);
    }
}