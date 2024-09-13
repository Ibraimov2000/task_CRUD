package com.todoapi.task.controller;

import com.todoapi.task.model.TaskRecord;
import com.todoapi.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Получить список всех задач", description = "Возвращает список всех задач в системе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задачи успешно получены"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    @GetMapping
    public List<TaskRecord> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Получить задачу по ID", description = "Возвращает задачу по ее уникальному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно найдена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @GetMapping("/task")
    public TaskRecord getTaskById(@Parameter(description = "ID задачи для поиска", required = true) @RequestParam Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Добавить новую задачу", description = "Добавляет новую задачу в список задач.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Задача успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PostMapping
    public TaskRecord addTask(@RequestBody TaskRecord taskRecord) {
        return taskService.addTask(taskRecord);
    }

    @Operation(summary = "Обновить задачу", description = "Обновляет описание или статус существующей задачи.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Задача успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена"),
            @ApiResponse(responseCode = "400", description = "Некорректный запрос")
    })
    @PutMapping
    public TaskRecord updateTask(@Parameter(description = "ID задачи для обновления", required = true) @RequestParam Long id,
                                 @RequestBody TaskRecord taskRecord) {
        return taskService.updateTask(id, taskRecord);
    }

    @Operation(summary = "Удалить задачу", description = "Удаляет задачу по ее уникальному идентификатору.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Задача успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Задача не найдена")
    })
    @DeleteMapping
    public void deleteTask(@Parameter(description = "ID задачи для удаления", required = true) @RequestParam Long id) {
        taskService.deleteTask(id);
    }
}
