package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.TaskService;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TaskControllerTest {
    private TaskController controller;
    private TaskService taskService;
    private TaskRepository taskRepository;

    List<Task> tasks;

    @BeforeEach
    void SetUp() {
        taskService = new TaskService(taskRepository);


        controller = new TaskController(taskService); // 주입
        Task task = new Task();
        task.setId(1L);
        task.setTitle("TASK 1");
        controller.create(task);
    }

    @Test
    void getTasks() {
        tasks = controller.list();
        assertThat(tasks).hasSize(1);
    }

    @Test
    void getTask() {
//        List<Task> tasks = controller.list(); // 맥락상 불필요한 코드 ==> 여길 지웠더니 널포인터 에러 발생
        // task 개별 값의 무엇이 들어있는지 확인하기 위해 tasks를 받아와야 한다. tasks 를 받아오지 못해 nullpointerexception 발생
        assertThat(tasks.get(0).getTitle()).isEqualTo("TASK 1");
    }

    @Test
    void ValidId() {
        Task task = controller.list().get(0);
        assertThat(task.getTitle()).isEqualTo("TASK 1");
    }

    @Test
    void InvalidId() {
        assertThatThrownBy(() -> controller.detail(2L)).isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void createTask(){
        int oldSize = controller.list().size();
        Task task = new Task();
        task.setTitle("TASK 2");
        controller.create(task);
        int newSize = controller.list().size();
        assertThat(newSize - oldSize).isEqualTo(1);
    }

    @Test
    void updateTask(){
        Task source = new Task();

        // for update()
        source.setTitle("TASK 2" + "!!");
        controller.update(1L, source);
        Task task = controller.list().get(0);
        assertThat(task.getTitle()).isEqualTo("TASK 2" + "!!");

        // for patch()
        source.setTitle("TASK 2");
        controller.patch(1L, source);
        assertThat(task.getTitle()).isEqualTo("TASK 2");
    }

    @Test
    void deleteTask(){
        int oldSize = controller.list().size();
        controller.delete(1L);
        int newSize = controller.list().size();
        assertThat(oldSize - newSize).isEqualTo(1);
    }
}
