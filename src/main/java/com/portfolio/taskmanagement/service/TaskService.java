package com.portfolio.taskmanagement.service;

import com.portfolio.taskmanagement.dto.TaskDTO;
import com.portfolio.taskmanagement.exception.ResourceNotFoundException;
import com.portfolio.taskmanagement.model.Category;
import com.portfolio.taskmanagement.model.Tag;
import com.portfolio.taskmanagement.model.Task;
import com.portfolio.taskmanagement.model.User;
import com.portfolio.taskmanagement.repository.CategoryRepository;
import com.portfolio.taskmanagement.repository.TagRepository;
import com.portfolio.taskmanagement.repository.TaskRepository;
import com.portfolio.taskmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public List<TaskDTO> getAllTasksForUser(Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }

        return convertToDTO(task);
    }

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus() != null ? taskDTO.getStatus() : Task.Status.TODO);
        task.setPriority(taskDTO.getPriority() != null ? taskDTO.getPriority() : Task.Priority.MEDIUM);
        task.setDueDate(taskDTO.getDueDate());
        task.setUser(user);

        if (taskDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(taskDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + taskDTO.getCategoryId()));
            task.setCategory(category);
        }

        if (taskDTO.getTagIds() != null && !taskDTO.getTagIds().isEmpty()) {
            List<Tag> tags = taskDTO.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId)))
                    .collect(Collectors.toList());
            task.setTags(tags);
        }

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }

        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());
        task.setDueDate(taskDTO.getDueDate());

        if (taskDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(taskDTO.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + taskDTO.getCategoryId()));
            task.setCategory(category);
        } else {
            task.setCategory(null);
        }

        if (taskDTO.getTagIds() != null) {
            List<Tag> tags = taskDTO.getTagIds().stream()
                    .map(tagId -> tagRepository.findById(tagId)
                            .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + tagId)))
                    .collect(Collectors.toList());
            task.setTags(tags);
        } else {
            task.setTags(new ArrayList<>());
        }

        Task updatedTask = taskRepository.save(task);
        return convertToDTO(updatedTask);
    }

    @Transactional
    public void deleteTask(Long id, Long userId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        if (!task.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }

        taskRepository.delete(task);
    }

    public List<TaskDTO> getTasksByStatus(Long userId, Task.Status status) {
        return taskRepository.findByUserIdAndStatus(userId, status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByPriority(Long userId, Task.Priority priority) {
        return taskRepository.findByUserIdAndPriority(userId, priority).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByCategory(Long userId, Long categoryId) {
        return taskRepository.findByUserIdAndCategoryId(userId, categoryId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksDueBetween(Long userId, LocalDateTime start, LocalDateTime end) {
        return taskRepository.findTasksByUserAndDueDateBetween(userId, start, end).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TaskDTO convertToDTO(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus());
        dto.setPriority(task.getPriority());
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());

        if (task.getCategory() != null) {
            dto.setCategoryId(task.getCategory().getId());
            dto.setCategoryName(task.getCategory().getName());
        }

        if (task.getTags() != null && !task.getTags().isEmpty()) {
            dto.setTagIds(task.getTags().stream().map(Tag::getId).collect(Collectors.toList()));
            dto.setTagNames(task.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        }

        return dto;
    }
}
