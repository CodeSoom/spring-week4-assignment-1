package com.codesoom.assignment.task.application;

import com.codesoom.assignment.task.TaskNotFoundException;
import com.codesoom.assignment.task.application.TaskService;
import com.codesoom.assignment.task.domain.Task;
import com.codesoom.assignment.task.domain.TaskRepository;
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

    private TaskService taskService;
    private TaskRepository taskRepository;
    private List<Task> tasks;
    private Task task;
    private Task updateTask;

    private static final String TITLE = "test";
    private static final String UPDATE_TITLE = "New Test1";
    private static final Long NOT_REGISTERED_ID = 100L;
    private static final Long REGISTERED_ID = 1L;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
        tasks = new ArrayList<>();
        task = new Task();
        updateTask = new Task();
        task.setTitle(TITLE);
    }

    @Nested
    @DisplayName("getTasks 메소드는")
    class Describe_getTasks {

        @Nested
        @DisplayName("등록된 할 일이 존재한다면")
        class Context_with_anything {

            @BeforeEach
            void registerTask() {
                taskService.createTask(task);
                tasks.add(task);

                given(taskRepository.findAll()).willReturn(tasks);
            }

            @Test
            @DisplayName("전체 할 일의 list를 리턴한다.")
            void it_return_every_task() {
                assertThat(taskService.getTasks()).hasSize(tasks.size());
            }
        }

        @Nested
        @DisplayName("등록된 할 일이 없다면")
         class Context_with_nothing {

            @BeforeEach
            void setUp() {
               given(taskRepository.findAll()).willReturn(new ArrayList<Task>());
            }

            @Test
            @DisplayName("빈 list를 리턴한다.")
            void it_return_empty_list() {
                assertThat(taskService.getTasks()).isEmpty();
                verify(taskRepository).findAll();
            }
        }
    }

    @Nested
    @DisplayName("getTask 메서드는")
    class Describe_getTask {

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_with_registered_ID {

            @BeforeEach
            void registerTask() {
                taskService.createTask(task);
                given(taskRepository.findById(REGISTERED_ID))
                        .willReturn(Optional.of(task));
            }

            @Test
            @DisplayName("해당 ID의 할 일을 리턴한다.")
            void it_return_task() {
                assertThat(taskService.getTask(REGISTERED_ID).getTitle())
                        .isEqualTo(TITLE);

                verify(taskRepository).findById(REGISTERED_ID);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_TaskNotFoundException() {
                assertThatThrownBy(() -> taskService.getTask(NOT_REGISTERED_ID))
                        .isInstanceOf(TaskNotFoundException.class);

                verify(taskRepository).findById(NOT_REGISTERED_ID);
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
                task.setId(REGISTERED_ID);
                task.setTitle(TITLE);
                return task;
            });
        }

        @Nested
        @DisplayName("할 일에 들어갈 내용을 받으면")
        class Context_with_some_title {

            @BeforeEach
            void setUp() {
                task.setTitle(TITLE);
                task = taskService.createTask(task);
            }

            @Test
            @DisplayName("할 일을 생성하고, 생성한 할 일을 리턴한다.")
            void it_create_task() {
                verify(taskRepository).save(any(Task.class));

                assertThat(task.getId()).isEqualTo(1L);
                assertThat(task.getTitle()).isEqualTo(TITLE);
            }
        }
    }

    @Nested
    @DisplayName("updateTask 메서드는")
    class Describe_updateTask {

        @BeforeEach
        void ready_task() {
            taskService.createTask(task);
            given(taskRepository.findById(REGISTERED_ID)).willReturn(Optional.of(task));
        }

        @Nested
        @DisplayName("등록된 ID값과 변경할 내용이 주어지면")
        class Context_with_registered_ID {

            @BeforeEach
            void setUp() {
                updateTask.setTitle(UPDATE_TITLE);
                task = taskService.updateTask(REGISTERED_ID,updateTask);
            }

            @Test
            @DisplayName("내용을 변경하고, 변경된 할 일을 리턴한다")
            void it_update_task() {
                assertThat(taskService.getTask(REGISTERED_ID).getTitle()).isEqualTo(UPDATE_TITLE);

                verify(taskRepository, times(2)).findById(REGISTERED_ID);
            }
        }

        @Nested
        @DisplayName("등록되지 않은 ID값이 주어지면")
        class Context_with_not_registered_ID {

            @Test
            @DisplayName("TaskNotFound 예외를 던집니다.")
            void it_throw_TaskNotFoundException() {
                assertThatThrownBy(() -> taskService.updateTask(NOT_REGISTERED_ID, updateTask))
                        .isInstanceOf(TaskNotFoundException.class);

                verify(taskRepository).findById(NOT_REGISTERED_ID);
            }
        }
    }

    @Nested
    @DisplayName("deleteTask 메서드는")
    class Describe_deleteTask {

        @BeforeEach
        void id_register() {
            taskService.createTask(task);
            given(taskRepository.findById(REGISTERED_ID))
                    .willReturn(Optional.of(task));
        }

        @Nested
        @DisplayName("등록된 ID값이 주어지면")
        class Context_with_registered_ID {

            @Test
            @DisplayName("해당 할 일을 삭제한다.")
            void it_delete_task() {
                Task task = taskService.deleteTask(REGISTERED_ID);

                assertThat(taskService.getTasks()).isEmpty();

                verify(taskRepository).findById(REGISTERED_ID);
                verify(taskRepository).delete(task);
            }
        }

        @Nested
        @DisplayName("등록되지 ID값이 주어지면")
        class Context_with_not_registered_ID {

            @Test
            @DisplayName("TaskNotFound 예외를 던집다.")
            void it_throw_TaskNotFoundException() {
                assertThatThrownBy(() -> taskService.deleteTask(NOT_REGISTERED_ID))
                        .isInstanceOf(TaskNotFoundException.class);

                verify(taskRepository).findById(NOT_REGISTERED_ID);
            }
        }
    }
}
