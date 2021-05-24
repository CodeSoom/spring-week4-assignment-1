package com.codesoom.assignment.controllers;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.application.TaskService;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import com.codesoom.assignment.infra.InMemoryTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("TaskControllerTest 클래스")
class TaskControllerTest {
    private TaskController controller;

    @BeforeEach
    void setUp() {
        TaskRepository taskRepository = new InMemoryTaskRepository();

        controller = new TaskController(new TaskService(taskRepository));
    }

    void createTestTask(String source) {
        Task task = new Task();
        task.setTitle(source);

        controller.create(task);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {

        @Nested
        @DisplayName("등록된 객체가 없다면")
        class Context_not_exist_task {
            @Test
            @DisplayName("빈 배열을 리턴한다.")
            void it_return_nothing() {
                assertThat(controller.list()).isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 객체가 1개 이상 존재한다면")
        class Context_exist_task {
            @BeforeEach
            void registerTask() {
                createTestTask("test");
                createTestTask("test2");
            }

            @Test
            @DisplayName("전체 객체의 list를 리턴한다.")
            void it_return_all() {
                assertThat(controller.list()).hasSize(2);
            }
        }
    }

    @Nested
    @DisplayName("detail 메서드는")
    class Describe_detail {

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {
            Long notRegisteredId = 100L;

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_exception() {
                assertThatThrownBy(() -> controller.detail(notRegisteredId))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_with_registered_ID {
            Long registeredId1 = 1L;
            Long registeredId2 = 2L;

            @BeforeEach
            void registerTask() {
                createTestTask("test");
                createTestTask("test2");
            }

            @Test
            @DisplayName("해당 ID의 객체를 리턴한다.")
            void it_return_task() {
                assertThat(controller.detail(registeredId1).getTitle()).isEqualTo("test");
                assertThat(controller.detail(registeredId2).getTitle()).isEqualTo("test2");
            }
        }
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Nested
        @DisplayName("객체에 들어갈 title 값을 받으면")
        class Context_right_title {
            @Test
            @DisplayName("객체를 생성하고, 생성된 객체를 리턴한다.")
            void it_create_task_and_return() {
                Task task = new Task();
                task.setTitle("test");

                assertThat(controller.create(task).getTitle()).isEqualTo("test");
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {
        Task task = new Task();

        @BeforeEach
        void ready_task_and_title() {
            createTestTask("test");
            task.setTitle("New test");
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {
            Long notRegisteredId = 100L;

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다")
            void it_throw_Exception() {
                assertThatThrownBy(() -> controller.update(notRegisteredId, task))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("등록된 ID값과 title값이 주어지면")
        class Context_right_ID_and_title {

            Long registeredId = 1L;

            @Test
            @DisplayName("title의 값을 변경하고, 해당 Task를 리턴한다.")
            void it_update_task() {
                assertThat(controller.update(registeredId, task).getTitle())
                        .isEqualTo("New test");
            }
        }
    }

    @Nested
    @DisplayName("patch 메서드는")
    class Describe_patch {
        Task task = new Task();

        @BeforeEach
        void ready_task_and_title() {
            createTestTask("test");
            task.setTitle("New test");
        }

        @Nested
        @DisplayName("올바르지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {
            Long notRegisteredId = 100L;

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_Exception() {
                assertThatThrownBy(() -> controller.patch(notRegisteredId, task))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("올바른 ID값과 title값이 주어지면")
        class Context_with_registered_ID {
            Long registeredID = 1L;

            @Test
            @DisplayName("title을 변경하고, 변경된 객체를 리턴한다.")
            void it_update_task() {
                assertThat(controller.patch(registeredID, task).getTitle())
                        .isEqualTo("New test");
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @BeforeEach
        void ready_task() {
            createTestTask("test");
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {
            Long notRegisteredId = 100L;

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_Exception() {
                assertThatThrownBy(() -> controller.delete(notRegisteredId))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_right_ID {
            Long registeredId = 1L;

            @Test
            @DisplayName("해당 객체를 삭제한다.")
            void it_delete_task() {
                assertThat(controller.detail(registeredId))
                        .isNotNull();

                controller.delete(registeredId);

                assertThat(controller.list())
                        .isEmpty();
            }
        }
    }
}
