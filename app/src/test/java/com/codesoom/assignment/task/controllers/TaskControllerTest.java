package com.codesoom.assignment.task.controllers;

import com.codesoom.assignment.task.TaskNotFoundException;
import com.codesoom.assignment.task.application.TaskService;
import com.codesoom.assignment.task.domain.Task;
import com.codesoom.assignment.task.domain.TaskRepository;
import com.codesoom.assignment.task.infra.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TaskControllerTest 클래스")
class TaskControllerTest {
    private TaskController controller;

    private static final Long NOT_REGISTERED_ID = 100L;
    private static final Long REGISTERED_ID = 1L;
    private static final String TITLE = "test";
    private Task task;
    private Task updateTask;

    @BeforeEach
    void setUp() {
        TaskRepository taskRepository = new InMemoryTaskRepository();
        controller = new TaskController(new TaskService(taskRepository));

        task = new Task();
        task.setTitle(TITLE);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {

        @Nested
        @DisplayName("등록된 할 일이 존재한다면")
        class Context_exist_task {
            @BeforeEach
            void registerTask() {
                controller.create(task);
            }

            @Test
            @DisplayName("전체 할 일의 list를 리턴한다.")
            void it_return_all() {
                assertThat(controller.list()).hasSize(1);
            }
        }

        @Nested
        @DisplayName("등록된 할 일이 없다면")
        class Context_not_exist_task {
            @Test
            @DisplayName("빈 배열을 리턴한다.")
            void it_return_nothing() {
                assertThat(controller.list()).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("detail 메서드는")
    class Describe_detail {

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_with_registered_ID {

            @BeforeEach
            void registerTask() {
                controller.create(task);
            }

            @Test
            @DisplayName("해당 ID의 할 일을 리턴한다.")
            void it_return_task() {
                assertThat(controller.detail(REGISTERED_ID).getTitle()).isEqualTo(TITLE);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_exception() {
                assertThatThrownBy(() -> controller.detail(NOT_REGISTERED_ID))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Nested
        @DisplayName("할 일에 들어갈 내용을 받으면")
        class Context_right_title {
            @Test
            @DisplayName("할 일을 생성하고, 생성된 할 일을 리턴한다.")
            void it_create_task_and_return() {
                Task task = new Task();
                task.setTitle(TITLE);

                assertThat(controller.create(task).getTitle()).isEqualTo(TITLE);
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {

        @BeforeEach
        void ready_task_and_title() {
            controller.create(task);
            updateTask = new Task();
            updateTask.setTitle("New test");
        }

        @Nested
        @DisplayName("등록된 ID값과 변경 할 내용이 주어지면")
        class Context_right_ID_and_title {

            @Test
            @DisplayName("내용을 변경하고, 변경된 할 일을 리턴한다.")
            void it_update_task() {
                assertThat(controller.update(REGISTERED_ID, updateTask).getTitle())
                        .isEqualTo("New test");
            }
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다")
            void it_throw_Exception() {
                assertThatThrownBy(() -> controller.update(NOT_REGISTERED_ID, updateTask))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("patch 메서드는")
    class Describe_patch {
        Task task = new Task();

        @BeforeEach
        void ready_task_and_title() {
            controller.create(task);
            updateTask = new Task();
            updateTask.setTitle("New test");
        }

        @Nested
        @DisplayName("올바른 ID값과 내용이 주어지면")
        class Context_with_registered_ID {

            @Test
            @DisplayName("내용을 변경하고, 변경된 할 일을 리턴한다.")
            void it_update_task() {
                assertThat(controller.patch(REGISTERED_ID, updateTask).getTitle())
                        .isEqualTo("New test");
            }
        }

        @Nested
        @DisplayName("올바르지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_Exception() {
                assertThatThrownBy(() -> controller.patch(NOT_REGISTERED_ID, updateTask))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @BeforeEach
        void ready_task() {
            controller.create(task);
        }

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_right_ID {

            @Test
            @DisplayName("해당 할 일을 삭제한다.")
            void it_delete_task() {
                assertThat(controller.detail(REGISTERED_ID))
                        .isNotNull();

                controller.delete(REGISTERED_ID);

                assertThat(controller.list())
                        .isEmpty();
            }
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_Exception() {
                assertThatThrownBy(() -> controller.delete(NOT_REGISTERED_ID))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }
}
