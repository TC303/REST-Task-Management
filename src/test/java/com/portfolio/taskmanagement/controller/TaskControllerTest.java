package com.portfolio.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.taskmanagement.dto.TaskDTO;
import com.portfolio.taskmanagement.model.Task;
import com.portfolio.taskmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TaskService taskService;

    private TaskDTO testTaskDTO;

    @BeforeEach
    void setUp() {
        testTaskDTO = new TaskDTO();
        testTaskDTO.setId(1L);
        testTaskDTO.setTitle("Test Task");
        testTaskDTO.setDescription("Test Description");
        testTaskDTO.setStatus(Task.Status.TODO);
        testTaskDTO.setPriority(Task.Priority.MEDIUM);
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() throws Exception {
        List<TaskDTO> tasks = Arrays.asList(testTaskDTO);
        when(taskService.getAllTasksForUser(1L)).thenReturn(tasks);

        mockMvc.perform(get("/api/tasks")
                .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Task"));
    }

    @Test
    void createTask_ShouldReturnCreatedTask() throws Exception {
        when(taskService.createTask(any(TaskDTO.class), eq(1L))).thenReturn(testTaskDTO);

        mockMvc.perform(post("/api/tasks")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTaskDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }
}
