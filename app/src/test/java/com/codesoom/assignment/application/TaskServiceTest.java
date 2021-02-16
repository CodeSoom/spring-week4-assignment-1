package com.codesoom.assignment.application;

import com.codesoom.assignment.Infra.InMemoryTaskRepository;
import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.Task;
import com.codesoom.assignment.domain.TaskRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("TaskService 클래스")
class TaskServiceTest {
    private TaskService taskService;
    private static final String BEFORE_TASK_TITLE = "before";
    private static final String UPDATE_TASK_TITLE = "updated";
    private static final String CREATE_TASK_TITLE = "created";

    @BeforeEach
    void setUp() {
        TaskRepository taskRepository = new TaskRepository();

        taskService = new TaskService(taskRepository);

        Task beforeTask = new Task(1L, BEFORE_TASK_TITLE);
        taskService.createTask(beforeTask);
    }

    @Nested
    @DisplayName("getTasks 메소드는")
    class Describe_getTasks {
        @BeforeEach
        void prepareOneMoreTask() {
            Task prepareTask = new Task(2L, BEFORE_TASK_TITLE);
            taskService.createTask(prepareTask);
        }

        @Test
        @DisplayName("할 일의 목록을 리턴한다")
        void itReturnsListOfTask() {
            List<Task> tasks = taskService.getTasks();
            List<Task> taskList = new ArrayList<>();
            taskList.add(taskService.getTask(1L));
            taskList.add(taskService.getTask(2L));

            assertEquals(tasks, taskList);
        }

        @Test
        @DisplayName("현재 가지고 있는 할 일의 갯수만큼 사이즈를 갖는다")
        void itHasSizeForTheNumberOfTask() {
            int newSize = taskService.getTasks().size();
            assertThat(newSize).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("getTask 메소드는")
    class Describe_getTask {
        @Nested
        @DisplayName("만약 저장되어 있는 할 일의 id가 주어진다면")
        class ContextWithValidId {
            private final Long givenValidId = 1L;

            @Test
            @DisplayName("주어진 id에 해당하는 할 일을 리턴한다")
            void itReturnsValidTask() {
                Task task = taskService.getTask(givenValidId);
                assertThat(task.getTitle()).isEqualTo(BEFORE_TASK_TITLE);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 할 일의 id가 주어진다면")
        class ContextWithInvalidId {
            private final Long givenInvalidId = 100L;

            @Test
            @DisplayName("할 일을 찾을 수 없다는 예외를 던진다")
            void itReturnsErrorMessageException() {
                assertThatThrownBy(() -> taskService.getTask(givenInvalidId))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("createTask 메서드는")
    class Describe_createTask {
        @Test
        @DisplayName("title을 입력받아 새로운 할 일을 생성하고 할 일을 리턴한다")
        void itReturnsNewTask() {
            Task newTask = new Task();
            newTask.setId(2L);
            newTask.setTitle(CREATE_TASK_TITLE);
            taskService.createTask(newTask);

            Task createdTask = taskService.getTask(newTask.getId());

            assertThat(createdTask.getId()).isEqualTo(2L);
            assertThat(createdTask.getTitle()).isEqualTo(CREATE_TASK_TITLE);
        }
    }

    @Nested
    @DisplayName("updateTask 메서드는")
    class Describe_updateTask {
        @Nested
        @DisplayName("만약 저장되어 있는 할 일의 id가 주어진다면")
        class ContextWithValidId {
            private final Long givenValidId = 1L;

            @Test
            @DisplayName("주어진 id에 해당하는 할 일의 title을 수정하고 할 일을 리턴한다")
            void itReturnsValidUpdatedTask() {
                Task source = new Task();
                source.setTitle(UPDATE_TASK_TITLE);
                taskService.updateTask(givenValidId, source);

                Task task = taskService.getTask(givenValidId);
                assertThat(task.getTitle()).isEqualTo(UPDATE_TASK_TITLE);
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 할 일의 id가 주어진다면")
        class ContextWithInvalidId {
            private final Long givenInvalidId = 100L;

            @Test
            @DisplayName("할 일을 찾을 수 없다는 예외를 던진다")
            void itReturnsErrorMessageException() {
                Task source = new Task();
                source.setTitle(UPDATE_TASK_TITLE);

                assertThatThrownBy(() -> taskService.updateTask(givenInvalidId, source))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }

    @Nested
    @DisplayName("deleteTask 메서드는")
    class Describe_deleteTask {
        @Nested
        @DisplayName("만약 저장되어 있는 할 일의 id가 주어진다면")
        class ContextWithValidId {
            private final Long givenValidId = 1L;

            @Test
            @DisplayName("유효한 id에 해당하는 할 일을 삭제하고 할 일의 리스트는 빈 문자열을 리턴한다")
            void itDeletesTaskAndReturnsEmptyString() {
                taskService.deleteTask(givenValidId);
                assertThat(taskService.getTasks().toString()).isEqualTo("[]");
            }
        }

        @Nested
        @DisplayName("만약 저장되어 있지 않은 할 일의 id가 주어진다면")
        class ContextWithInvalidId {
            private final Long givenInvalidId = 100L;

            @Test
            @DisplayName("할 일을 찾을 수 없다는 예외를 던진다")
            void itReturnsErrorMessageException() {
                assertThatThrownBy(() -> taskService.deleteTask(givenInvalidId))
                        .isInstanceOf(TaskNotFoundException.class);
            }
        }
    }
}
