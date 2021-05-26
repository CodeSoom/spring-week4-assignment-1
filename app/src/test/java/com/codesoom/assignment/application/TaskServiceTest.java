package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    private static final String TASK_TITLE = "test";
    private static final String UPDATE_POSTFIX = "|||";

    private TaskService taskService;


    @BeforeEach
    void setUp(){
        TaskRepository taskRepository = new TaskRepository();

        //subject
        taskService = new TaskService(taskRepository);

        //fixtures
        Task task = new Task();
        task.setTitle("test");

        taskService.createTask(task);
    }

    @DisplayName("TASK_TITLE을 가져오는 테스트")
    @Test
    void getTasks(){
        List<Task> tasks = taskService.getTasks();
        assertThat(tasks).hasSize(1);

        Task task = tasks.get(0);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
    }

    @Test
    void getTaskWithValidId(){
        Task task = taskService.getTask(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE);

    }

    @Test
    void getTaskWithInvalidId(){
        assertThatThrownBy(()-> taskService.getTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void createTask(){
        int oldSize = taskService.getTasks().size();

        Task task = new Task();
        task.setTitle("test");

        taskService.createTask(task);

        int newSize = taskService.getTasks().size();

        assertThat(newSize-oldSize).isEqualTo(1);
    }
    @Test
    void updateTaskWithExistedID(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);
        taskService.updateTask(1L,source);

        Task task = taskService.getTask(1L);
        assertThat(task.getTitle()).isEqualTo(TASK_TITLE + UPDATE_POSTFIX);
    }

    @Test
    void updateTaskWithNotExistedID(){
        Task source = new Task();
        source.setTitle(TASK_TITLE + UPDATE_POSTFIX);

        assertThatThrownBy(()-> taskService.updateTask(100L,source))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTaskWithExsitedID(){
        int oldSize = taskService.getTasks().size();

        taskService.deleteTask(1L);

        int newSize = taskService.getTasks().size();

        assertThat(oldSize-newSize).isEqualTo(1);
    }

    @Test
    void deleteTaskWithNotExsitedID(){
        assertThatThrownBy(()->taskService.deleteTask(100L))
                .isInstanceOf(TaskNotFoundException.class);
    }

}