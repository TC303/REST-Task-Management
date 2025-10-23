package com.portfolio.taskmanagement.controller;

import com.portfolio.taskmanagement.dto.TaskDTO;
import com.portfolio.taskmanagement.model.Task;
import com.portfolio.taskmanagement.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "APIs for managing tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Get all tasks for a user")
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getAllTasksForUser(userId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id, @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getTaskById(id, userId));
    }

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskDTO taskDTO, @RequestParam Long userId) {
        return new ResponseEntity<>(taskService.createTask(taskDTO, userId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskDTO taskDTO,
            @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO, userId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @RequestParam Long userId) {
        taskService.deleteTask(id, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get tasks by status")
    public ResponseEntity<List<TaskDTO>> getTasksByStatus(
            @PathVariable Task.Status status,
            @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getTasksByStatus(userId, status));
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Get tasks by priority")
    public ResponseEntity<List<TaskDTO>> getTasksByPriority(
            @PathVariable Task.Priority priority,
            @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getTasksByPriority(userId, priority));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get tasks by category")
    public ResponseEntity<List<TaskDTO>> getTasksByCategory(
            @PathVariable Long categoryId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(taskService.getTasksByCategory(userId, categoryId));
    }

    @GetMapping("/due-between")
    @Operation(summary = "Get tasks due between dates")
    public ResponseEntity<List<TaskDTO>> getTasksDueBetween(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(taskService.getTasksDueBetween(userId, start, end));
    }
}
