package com.portfolio.taskmanagement.service;

import com.portfolio.taskmanagement.dto.TaskDTO;
import com.portfolio.taskmanagement.exception.ResourceNotFoundException;
import com.portfolio.taskmanagement.model.Task;
import com.portfolio.taskmanagement.model.User;
import com.portfolio.taskmanagement.repository.CategoryRepository;
import com.portfolio.taskmanagement.repository.TagRepository;
import com.portfolio.taskmanagement.repository.TaskRepository;
import com.portfolio.taskmanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TaskService taskService;

    private User testUser;
    private Task testTask;
    private TaskDTO testTaskDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setEmail("test@example.com");

        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus(Task.Status.TODO);
        testTask.setPriority(Task.Priority.MEDIUM);
        testTask.setUser(testUser);

        testTaskDTO = new TaskDTO();
        testTaskDTO.setTitle("Test Task");
        testTaskDTO.setDescription("Test Description");
        testTaskDTO.setStatus(Task.Status.TODO);
        testTaskDTO.setPriority(Task.Priority.MEDIUM);
    }

    @Test
    void getAllTasksForUser_ShouldReturnListOfTasks() {
        when(taskRepository.findByUserId(1L)).thenReturn(Arrays.asList(testTask));

        List<TaskDTO> result = taskService.getAllTasksForUser(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository, times(1)).findByUserId(1L);
    }

    @Test
    void getTaskById_WhenTaskExists_ShouldReturnTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));

        TaskDTO result = taskService.getTaskById(1L, 1L);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_WhenTaskNotExists_ShouldThrowException() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            taskService.getTaskById(1L, 1L);
        });
    }

    @Test
    void createTask_ShouldCreateAndReturnTask() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        TaskDTO result = taskService.createTask(testTaskDTO, 1L);

        assertNotNull(result);
        assertEquals("Test Task", result.getTitle());
        verify(userRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask_WhenTaskExists_ShouldDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));
        doNothing().when(taskRepository).delete(testTask);

        taskService.deleteTask(1L, 1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(testTask);
    }
}
