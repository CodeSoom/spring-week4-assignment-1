package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.TaskService;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TaskControllerTest {
    private TaskController controller;
    private TaskService taskService;
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp(){
        taskService = new TaskService(taskRepository);
        controller = new TaskController(taskService);
    }

    @Test
    void list() {
        // list()메소드가 비어있는지 확인
        assertThat(controller.list()).isEmpty();
    }

    @Test
    void createNewTask() {
        Task task = new Task();
        task.setTitle("Test1");
        controller.create(task);

        task.setTitle("Test2");
        controller.create(task);

        assertThat(controller.list()).isNotEmpty();

        assertThat(controller.list()).hasSize(2);

        assertThat(controller.list().get(0).getId()).isEqualTo(1L);
        assertThat(controller.list().get(0).getTitle()).isEqualTo("Test1");

        assertThat(controller.list().get(1).getId()).isEqualTo(2L);
        assertThat(controller.list().get(1).getTitle()).isEqualTo("Test2");
    }
}
