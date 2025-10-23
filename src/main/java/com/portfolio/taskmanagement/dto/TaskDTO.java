package com.portfolio.taskmanagement.dto;

import com.portfolio.taskmanagement.model.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Status is required")
    private Task.Status status;

    @NotNull(message = "Priority is required")
    private Task.Priority priority;

    private LocalDateTime dueDate;

    private Long categoryId;

    private String categoryName;

    private List<Long> tagIds;

    private List<String> tagNames;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
