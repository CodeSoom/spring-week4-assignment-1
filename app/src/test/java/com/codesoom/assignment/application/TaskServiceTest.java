package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@DataJpaTest
class TaskServiceTest {
    private TaskRepository taskRepository;
    private TaskService taskService;
    String TASK_TITLE = "test";
    String CREATE_POSIX = "...";

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);

        // subjecct
        taskService = new TaskService(taskRepository);

        setUpFixtures();
        setUpSaveTask();
    }

    void setUpFixtures() {
        List<Task> tasks = new ArrayList<>();

        Task task = new Task();
        task.setTitle(TASK_TITLE);

        tasks.add(task);

        given(taskRepository.findAll()).willReturn(tasks);
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(taskRepository.findById(100L)).willReturn(Optional.empty());
    }

    void setUpSaveTask() {
        given(taskRepository.save(any(Task.class))).will(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(2L);
            return task;
        });
    }

    @Test
    void getTasks(){
        List<Task> tasks = taskService.getTasks();
        verify(taskRepository).findAll();
    }


    @Test
    void getTaskWitExistedId() {
        Task task = taskService.getTask(1L);

        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);

        verify(taskRepository).findById(1L);
    }

    @Test
    void getTaskWitInvalidId() {
        assertThatThrownBy(() -> taskService.getTask(100L)).isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository).findById(100L);

    }

    @Test
    void createTask(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + CREATE_POSIX);

        Task task = taskService.createTask(source); // service 는 taskRepository 에 의존적임을 드러내는 테스트코드
        verify(taskRepository).save(any(Task.class)); // <- 너무 내부 구현을 드러내는 것 같다면 숨기는게 맞다.
        assertThat(task.getId()).isEqualTo(2L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + CREATE_POSIX);
    }

    @Test
    void updateTaskWithExistedID(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + "!!!");
        Task task = taskService.updateTask(1L, source);
        verify(taskRepository).findById(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + "!!!");
    }

    @Test
    void updateTaskWithNoExistedID(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + "!!!");
        assertThatThrownBy(() -> taskService.updateTask(100L, source))
                .isInstanceOf(TaskNotFoundException.class);
        verify(taskRepository).findById(100L);
    }

    @Test
    void deleteTask(){
        taskService.deleteTask(1L);
        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(any(Task.class));
    }

    @Test
    void deleteWithNotExistedID(){
        assertThatThrownBy(() -> taskService.deleteTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
        verify(taskRepository).findById(100L);
    }
}
