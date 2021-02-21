package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.application.TaskService;
import com.codesoom.assignment.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TaskControllerTest {
    private TaskController controller;

    // 가능한 것
    // 1. Real object
    // 2. Mock object           => 타입이 필요함.
    // 3. Spy -> Proxy pattern  => 진짜 오브젝트가 필요함
    private TaskService taskService;

    private static final String TASK_TITLE = "Test1";
    private static final String CREATE_TITLE = "Test2";
    private static final String UPDATE_TITLE = "Rename task";

    @BeforeEach
    void setUp(){
//        taskService = spy(new TaskService());  // spy 진짜 오브젝트가 필요하기 때문에 new로 선언해준다.
        taskService = mock(TaskService.class); // Mock obejct는 타입(interface)이 필요하다. class는 구현체

        List<Task> tasks = new ArrayList<>();
        Task task = new Task(TASK_TITLE);
        tasks.add(task);

        // list 전체를 return
        given(taskService.getTasks()).willReturn(tasks);

        // 해당 list만 return
        given(taskService.getTask(1L)).willReturn(task);

        // 존재하지 않는 list를 요청할 때는 Exception을 뱉는다.
        given(taskService.getTask(100L))
                .willThrow(new TaskNotFoundException(100L));


        given(taskService.updateTask(eq(100L), any(Task.class))) // any() 아무거나 들어와도 돼, 또는 any(Task.class)만 들어와
                .willThrow(new TaskNotFoundException(100L));

        given(taskService.deleteTask(100L))
                .willThrow(new TaskNotFoundException(100L));

        controller = new TaskController(taskService);
    }

    @Test
    @DisplayName("Task List가 비어있는지 확인한다.")
    void listWithoutTasks() {
        // TODO : service -> returns empty list
        given(taskService.getTasks()).willReturn(new ArrayList<>());

        // taskService.getTasks
        // Controller -> Spy -> Real Object (Proxy pattern)

        assertThat(controller.list()).isEmpty();

        verify(taskService).getTasks();
    }

    @Test
    @DisplayName("Task List가 존재하는지 확인한다.")
    void listWitSomeTasks() {
        // TODO : service -> returns list that contains one task.
        assertThat(controller.list()).isNotEmpty();

        verify(taskService).getTasks();
    }

    @Test
    @DisplayName("특정 Task가 존재하는지 확인한다.")
    void detailWithExistedId() {
        Task task = controller.detail(1L);

        assertThat(task).isNotNull();
    }

    @Test
    @DisplayName("특정 Task가 존재하지 않으면 예외를 던진다.")
    void detailWithNotExistedId() {
        // tasks/100이 등록되어있지않기 때문에 당연히 null이된다.
//        Task task = controller.detail(100L);
//        assertThat(task).isNull();

        // 하지만 등록되어 있지 않은 것을 요청할때 Exception을 뱉어주는것이 좋기 때문에 위 처럼 하지 않는다.
        assertThatThrownBy(() -> controller.detail(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("새로운 Task를 추가한다.")
    void createNewTask() {
        Task task = new Task(CREATE_TITLE);

        controller.create(task);

        verify(taskService).createTask(task);
    }

    @Test
    @DisplayName("특정 Task의 값을 변경한다.")
    void updateWithExistedTask() {
        Task task = new Task(UPDATE_TITLE);

        controller.update(1L, task);
        verify(taskService).updateTask(1L, task);
    }

    @Test
    @DisplayName("특정 Task가 존재하지 않으면 예외를 던진다.")
    void updateWithNotExistedTask() {
        Task task = new Task(UPDATE_TITLE);

        // 마찬가지로 존재하지 않는 100L을 테스트 할 때는 Exception을 뱉어준다.
//        controller.update(100L, task);
//        verify(taskService).updateTask(100L, task);

        assertThatThrownBy(() -> controller.update(100L,task))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    @DisplayName("특정 Task를 삭제한다.")
    void deleteWithExistedTask() {
        controller.delete(1L);
        verify(taskService).deleteTask(1L);
    }

    @Test
    @DisplayName("특정 Task가 존재하지 않으면 예외를 던진다.")
    void delteWithNotExistedTask() {
        // 마찬가지로 존재하지 않는 100L을 테스트 할 때는 Exception을 뱉어준다.
//        controller.delete(100L);
//        verify(taskService).deleteTask(100L);

        assertThatThrownBy(() -> controller.delete(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }
}
