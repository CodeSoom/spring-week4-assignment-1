package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TaskServiceTest {
    private static final String TASK_TITLE = "Test";
    private static final String CREATE_POSTFIX = "CREATE";
    private static final String UPDATE_POSTFIX = "!!!";

    private TaskService taskService;
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        // new TaskRepository를 사용하는것이 아닌
        // mock을 사용하여 가짜 객체를 주입시킨다.
        taskRepository = mock(TaskRepository.class);

        taskService = new TaskService(taskRepository);
        setUpFixtures();
        setUpSaveTask();
    }

    void setUpFixtures() {
        List<Task> tasks = new ArrayList<>();

        Task task = new Task(TASK_TITLE);

        tasks.add(task);

        given(taskRepository.findAll()).willReturn(tasks);

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        given(taskRepository.findById(100L)).willReturn(Optional.empty()); // 100일때는 empty를 return
    }

    void setUpSaveTask() {
        given(taskRepository.save(any(Task.class))).will(invocation -> {
           Task task = invocation.getArgument(0);
           task.setId(2L);
           return task;
        });
    }

    @Test
    @DisplayName("Task 전체를 조회하며 사이즈를 확인한다.")
    void getTasks() {
        List<Task> tasks = taskService.getTasks();
        verify(taskRepository).findAll();

        assertThat(tasks).hasSize(1);

        Task task = tasks.get(0);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    @DisplayName("특정 Task를 조회하여 값을 확인한다.")
    void getTaskWithExistedId() {
        Task task = taskService.getTask(1L);

        verify(taskRepository).findById(1L);

        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    @DisplayName("특정 Task를 찾을 수 없다면 예외를 던진다.")
    void getTaskWithInvalidId() {
        assertThatThrownBy(() -> taskService.getTask(100L))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository).findById(100L);
    }

    @Test
    @DisplayName("새로운 Task를 생성한다.")
    void createTask() {
        Task source = new Task(TASK_TITLE + CREATE_POSTFIX);

        Task task = taskService.createTask(source);

        verify(taskRepository).save(any(Task.class));

        assertThat(task.getId()).isEqualTo(2L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + CREATE_POSTFIX);
    }

    @Test
    @DisplayName("특정 Task의 값을 변경한다.")
    void updateTaskWithExistedId() {
        Task source = new Task(TASK_TITLE + UPDATE_POSTFIX);

        Task task = taskService.updateTask(1L, source);

        verify(taskRepository).findById(1L);

        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + UPDATE_POSTFIX);
    }

    @Test
    @DisplayName("특정 Task를 찾을 수 없다면 예외를 던진다.")
    void updateTaskWithNotExistedId() {
        Task source = new Task(TASK_TITLE + UPDATE_POSTFIX);

        assertThatThrownBy(() -> taskService.updateTask(100L, source))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository).findById(100L);
    }

    @Test
    @DisplayName("특정 Task를 삭제한다.")
    void deleteTaskWithExsitedId() {
        taskService.deleteTask(1L);

        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(any(Task.class));
    }

    @Test
    @DisplayName("특정 Task를 찾을 수 없다면 예외를 던진다.")
    void deleteTaskWithNotExsitedId() {
        assertThatThrownBy(() -> taskService.deleteTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
        verify(taskRepository).findById(100L);
    }
}
