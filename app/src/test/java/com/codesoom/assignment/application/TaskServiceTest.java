package com.codesoom.assignment.application;

import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@DisplayName("TaskService 클래스")
class TaskServiceTest {

    private static final String TASK_TITLE = "test";
    private TaskService taskService;
    private TaskRepository taskRepository;
    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
        tasks = new ArrayList<>();
    }

    Task createTestTask(String source) {
        Task task = new Task();
        task.setTitle(source);
        taskService.createTask(task);

        return task;
    }

    @Nested
    @DisplayName("getTasks 메소드는")
    class Describe_getTasks {

        @Nested
        @DisplayName("등록된 객체가 없다면")
         class Context_with_nothing {
            @BeforeEach
            void setUp() {
               given(taskRepository.findAll()).willReturn(tasks);
            }

            @Test
            @DisplayName("빈 list를 리턴한다.")
            void it_return_empty_list() {
                assertThat(taskService.getTasks()).isEmpty();
                verify(taskRepository).findAll();
            }
        }

        @Nested
        @DisplayName("등록된 객체가 1개 이상 존재한다면")
        class Context_with_anything {
            @BeforeEach
            void registerTask() {
                Task task = createTestTask("test");
                tasks.add(task);

                given(taskRepository.findAll()).willReturn(tasks);
            }

            @Test
            @DisplayName("전체 객체의 list를 리턴한다.")
            void it_return_every_task() {
                assertThat(taskService.getTasks()).hasSize(1);
            }
        }
    }

    @Nested
    @DisplayName("getTask 메서드는")
    class Describe_getTask {

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {

            Long notRegisteredId = 100L;

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_TaskNotFoundException() {
                assertThatThrownBy(() -> taskService.getTask(notRegisteredId))
                        .isInstanceOf(TaskNotFoundException.class);

                verify(taskRepository).findById(notRegisteredId);
            }
        }

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_with_registered_ID {
            Long registeredId1 = 1L;

            @BeforeEach
            void registerTask() {
                Task task = createTestTask("test");
                given(taskRepository.findById(registeredId1))
                        .willReturn(Optional.of(task));
            }

            @Test
            @DisplayName("해당 ID의 객체를 리턴한다.")
            void it_return_task() {
                assertThat(taskService.getTask(registeredId1).getTitle())
                        .isEqualTo(TASK_TITLE);

                verify(taskRepository).findById(registeredId1);
            }
        }
    }

    @Nested
    @DisplayName("createTask 메서드는")
    class Describe_createTask {

        @BeforeEach
        void setUp() {
            given(taskRepository.save(any(Task.class))).will(invocation -> {
                Task task = invocation.getArgument(0);
                task.setId(1L);
                task.setTitle("test");
                return task;
            });
        }

        @Nested
        @DisplayName("객체에 들어갈 Title을 받으면")
        class Context_with_some_title {
            Task task = new Task();

            @BeforeEach
            void setUp() {
                Task source = new Task();
                source.setTitle(TASK_TITLE);
                task = taskService.createTask(source);
            }

            @Test
            @DisplayName("객체를 생성하고, 생성한 객체를 리턴한다.")
            void it_create_task() {
                verify(taskRepository).save(any(Task.class));

                assertThat(task.getId()).isEqualTo(1L);
                assertThat(task.getTitle()).isEqualTo(TASK_TITLE);
            }
        }
    }

    @Nested
    @DisplayName("updateTask 메서드는")
    class Describe_updateTask {
        Task task = new Task();

        @BeforeEach
        void ready_task() {
            task = createTestTask("test");
            given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {
            Long notRegisteredId = 100L;

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_TaskNotFoundException() {
                assertThatThrownBy(() -> taskService.updateTask(notRegisteredId, task))
                        .isInstanceOf(TaskNotFoundException.class);

                verify(taskRepository).findById(notRegisteredId);
            }
        }

        @Nested
        @DisplayName("등록된 ID값과 변경할 title이 주어지면")
        class Context_with_registered_ID {
            String updateText = "New Test1";
            Long registeredId = 1L;

            @BeforeEach
            void setUp() {
                Task source = new Task();
                source.setTitle(updateText);
                task = taskService.updateTask(registeredId,source);
            }

            @Test
            @DisplayName("title을 변경하고, 변경된 객체를 리턴한다")
            void it_update_task() {
                assertThat(taskService.getTask(registeredId).getTitle()).isEqualTo("New Test1");

                verify(taskRepository, times(2)).findById(registeredId);
            }
        }
    }

    @Nested
    @DisplayName("deleteTask 메서드는")
    class Describe_deleteTask {

        @BeforeEach
        void id_register() {
            Task task = createTestTask("test");
            given(taskRepository.findById(1L))
                    .willReturn(Optional.of(task));
        }

        @Nested
        @DisplayName("등록되지 ID값이 주어지면")
        class Context_with_not_registered_ID {
            Long notRegisteredId = 100L;

            @Test
            @DisplayName("TaskNotFound 예외를 던집다.")
            void it_throw_TaskNotFoundException() {
                assertThatThrownBy(() -> taskService.deleteTask(notRegisteredId))
                        .isInstanceOf(TaskNotFoundException.class);

                verify(taskRepository).findById(notRegisteredId);
            }
        }

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_with_registered_ID {
            Long registeredId = 1L;

            @Test
            @DisplayName("해당 객체를 삭제한다.")
            void it_delete_task() {
                Task task = taskService.deleteTask(registeredId);

                assertThat(taskService.getTasks()).isEmpty();

                verify(taskRepository).findById(registeredId);
                verify(taskRepository).delete(task);
            }
        }
    }
}
