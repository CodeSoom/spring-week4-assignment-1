package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.models.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    private TaskService taskService;
    private static final String TASK_TITLE = "test";
    private static final String POST_FIX = "???";

    @BeforeEach
    void setup() {
        taskService = new TaskService();

        Task task = new Task();
        task.setTitle(TASK_TITLE);

        taskService.createTask(task);
    }


    @Test
    void getTasks() {
        List<Task> tasks = taskService.getTasks();

        assertThat(tasks).hasSize(1);

        Task task = tasks.get(0);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithValidId() {
        Task task = taskService.getTask(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithoutInvalidId() {
        assertThatThrownBy(() -> taskService.getTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void createTask() {
        int oldSize = taskService.getTasks().size();

        Task task = new Task();
        task.setTitle(TASK_TITLE);

        taskService.createTask(task);

        int newSize = taskService.getTasks().size();

        assertThat(taskService.createTask(task).getTitle()).isEqualTo(TASK_TITLE);
        assertThat(newSize - oldSize).isEqualTo(1);
    }

    @Test
    void updateTaskWithValidId() {
       Task source = new Task();
       source.setTitle(TASK_TITLE + POST_FIX);

       taskService.updateTask(1L, source);

       Task task = taskService.getTask(1L);
       assertThat(task.getTitle()).isEqualTo(TASK_TITLE + POST_FIX);
    }

    @Test
    void updateTaskWithInvalidId() {
        Task source = new Task();
        assertThatThrownBy(() -> taskService.updateTask(100L, source))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTaskWithValidId() {
        int oldSize = taskService.getTasks().size();

        taskService.deleteTask(1L);

        int newSize = taskService.getTasks().size();

        assertThat(oldSize - newSize).isEqualTo(1);
    }

    @Test
    void deleteTaskWithInvalidId() {
        assertThatThrownBy(() -> taskService.deleteTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }
}