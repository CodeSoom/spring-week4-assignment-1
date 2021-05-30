package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import com.codesoom.assignment.infra.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DataJpaTest
class TaskServiceTest {
    private static final String TASK_TITLE = "test";
    private static final String UPDATE_POSTFIX = "|||";
    private static final String CREATE_POSTFIX = "...";

    private TaskService taskService;

    private  TaskRepository taskRepository;


    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        //subject
        taskService = new TaskService(taskRepository);
        setUpFixtures();
        setUpSaveTask();
    }
    void setUpFixtures() {
        List<Task> tasks = new ArrayList<>();

        Task task = new Task();
        task.setTitle("test");

        tasks.add(task);
        given(taskRepository.findAll()).willReturn(tasks);
        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(taskRepository.findById(100L)).willReturn(Optional.empty());
    }
    void setUpSaveTask(){
        given(taskRepository.save(any(Task.class))).will(invocation -> {
            Task task = invocation.getArgument(0);
            task.setId(2L);
            return task;
        });
    }

    @DisplayName("TASK_TITLE을 가져오는 테스트")
    @Test
    void getTasks(){
        List<Task> tasks = taskService.getTasks();

        verify(taskRepository).findAll();

        assertThat(tasks).hasSize(1);

        Task task = tasks.get(0);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithExistedId(){
        Task task = taskService.getTask(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);

        verify(taskRepository).findById(1L);
    }

    @Test
    void getTaskWithInvalidId(){
        assertThatThrownBy(()-> taskService.getTask(100L))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository).findById(100L);
    }

    @Test
    void createTask(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + CREATE_POSTFIX);

        Task task = taskService.createTask(source);

        verify(taskRepository).save(any(Task.class));

        assertThat(task.getId()).isEqualTo(2L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + CREATE_POSTFIX);
    }

    @Test
    void updateTaskWithExistedID(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);

        Task task = taskService.updateTask(1L,source);

        verify(taskRepository).findById(1L);

        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + UPDATE_POSTFIX);
    }

    @Test
    void updateTaskWithNotExistedID(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);

        assertThatThrownBy(()-> taskService.updateTask(100L,source))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository).findById(100L);
    }

    @Test
    void deleteTaskWithExsitedID(){

        taskService.deleteTask(1L);

        verify(taskRepository).findById(1L);
        verify(taskRepository).delete(any(Task.class));

    }

    @Test
    void deleteTaskWithNotExsitedID(){
        assertThatThrownBy(()->taskService.deleteTask(100L))
                .isInstanceOf(TaskNotFoundException.class);

        verify(taskRepository).findById(100L);
    }

}