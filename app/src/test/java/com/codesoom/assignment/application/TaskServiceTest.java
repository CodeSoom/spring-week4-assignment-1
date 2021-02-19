package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.infra.InMemoryTaskRepository;
import com.codesoom.assignment.models.Task;
import com.codesoom.assignment.models.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TaskServiceTest {
    private TaskService taskService;
    private static final String TASK_TITLE = "test";
    private static final String POST_FIX = "???";

    private TaskRepository taskRepository;

    @BeforeEach
    void setup() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);

        List<Task> tasks = new ArrayList<>();

        Task task = new Task();
        task.setTitle(TASK_TITLE);

        tasks.add(task);

        given(taskRepository.findAll()).willReturn(tasks);

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(taskRepository.findById(100L)).willReturn(Optional.empty());
    }


    @Test
    void getTasks() {
        List<Task> tasks = taskService.getTasks();

        verify(taskRepository).findAll();

        assertThat(tasks).hasSize(1);

        Task task = tasks.get(0);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithValidId() {
        Task task = taskService.getTask(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);

        verify(taskRepository).findById(1L);
    }

    @Test
    void getTaskWithoutInvalidId() {
        assertThatThrownBy(() -> taskService.getTask(100L))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository).findById(100L);
    }

    @Test
    void createTask() {
        Task task = new Task();
        task.setTitle(TASK_TITLE);

        taskService.createTask(task);

        verify(taskRepository).save(any(Task.class));
    }

//    @Test
//    void updateTaskWithValidId() {
//       Task source = new Task();
//       source.setTitle(TASK_TITLE + POST_FIX);
//
//       taskService.updateTask(1L, source);
//
//       Task task = taskService.getTask(1L);
//       assertThat(task.getTitle()).isEqualTo(TASK_TITLE + POST_FIX);
//    }
//
//    @Test
//    void updateTaskWithInvalidId() {
//        Task source = new Task();
//        assertThatThrownBy(() -> taskService.updateTask(100L, source))
//                .isInstanceOf(TaskNotFoundException.class);
//    }
//
//    @Test
//    void deleteTaskWithValidId() {
//        int oldSize = taskService.getTasks().size();
//
//        taskService.deleteTask(1L);
//
//        int newSize = taskService.getTasks().size();
//
//        assertThat(oldSize - newSize).isEqualTo(1);
//    }
//
//    @Test
//    void deleteTaskWithInvalidId() {
//        assertThatThrownBy(() -> taskService.deleteTask(100L))
//                .isInstanceOf(TaskNotFoundException.class);
//    }
}