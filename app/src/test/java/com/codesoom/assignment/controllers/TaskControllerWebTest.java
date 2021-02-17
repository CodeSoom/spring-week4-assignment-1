package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.application.TaskService;
import com.codesoom.assignment.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerWebTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        List<Task> tasks = new ArrayList<>();

        Task task = new Task();
        task.setTitle("Test Task");
        tasks.add(task);

        given(taskService.getTasks()).willReturn(tasks);
        given(taskService.getTask(1L)).willReturn(task);

        given(taskService.getTask(100L))
                .willThrow(new TaskNotFoundException(100L));
        given(taskService.updateTask(eq(100L), any(Task.class)))
                .willThrow(new TaskNotFoundException(100L));
        given(taskService.deleteTask(eq(100L)))
                .willThrow(new TaskNotFoundException(100L));
    }

    @Test
    void list() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Task")));

        verify(taskService).getTasks();
    }

    @Test
    void detailWithValidId() throws Exception {
        mockMvc.perform(get("/tasks/1"))
                .andExpect(status().isOk());

        verify(taskService).getTask(1L);
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"title\":\"Renamed Task\"}")
        )
                .andExpect(status().isCreated());

        verify(taskService).createTask(any(Task.class));
    }

    @Test
    void updateTaskWithValidId() throws Exception {
        mockMvc.perform(patch("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Renamed Task\"}")
        )
                .andExpect(status().isOk());
        
        verify(taskService).updateTask(eq(1L), any(Task.class));
    }

    @Test
    void updateTaskWithInvalidId() throws Exception {
        mockMvc.perform(
                patch("/tasks/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Renamed Task\"}")
        )
                .andExpect(status().isNotFound());

        verify(taskService).updateTask(eq(100L), any(Task.class));
    }

    @Test
    void deleteTaskValidId() throws Exception {
        mockMvc.perform(
                delete("/tasks/1"))
                .andExpect(status().isNoContent());
        verify(taskService).deleteTask(1L);
    }

    @Test
    void deleteTaskInvalidId() throws Exception {
        mockMvc.perform(
                delete("/tasks/100"))
                .andExpect(status().isNotFound());

        verify(taskService).deleteTask(100L);
    }

}
