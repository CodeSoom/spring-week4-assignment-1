package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Task 클래스")
class TaskTest {
    @Nested
    @DisplayName("setTitle 메서드는")
    class Describe_setTitle {
        @Nested
        @DisplayName("만약 String 타입의 유효한 title이 주어진다면")
        class ContextWithValidTitle {
            private String givenValidTitle = "task";

            @Test
            @DisplayName("해당 할 일에 title을 설정할 수 있다")
            void itSetsTitleToTask () {
                Task task = new Task();
                task.setTitle(givenValidTitle);

                assertThat(task.getTitle()).isEqualTo(givenValidTitle);
            }
        }
    }

    @Nested
    @DisplayName("setId 메서드는")
    class Describe_setId {
        @Nested
        @DisplayName("만약 Long 타입의 유효한 id가 주어진다면")
        class ContextWithValidId {
            private Long givenValidId = 1L;

            @Test
            @DisplayName("해당 할 일에 id를 설정할 수 있다")
            void itSetsIdToTask() {
                Task task = new Task();
                task.setId(givenValidId);

                assertThat(task.getId()).isEqualTo(givenValidId);
            }
        }
    }

    @Nested
    @DisplayName("equals 메서드는")
    class Describe_equals {
        private Task taskA, taskB;

        @Nested
        @DisplayName("만약 id가 같고 title가 다른 두 개의 할 일이 주어진다면")
        class ContextWithSameIdAndDifferentTitle {
            @BeforeEach
            void prepareTask() {
                taskA = new Task(2L, "taskA");
                taskB = new Task(2L, "taskB");
            }

            @Test
            @DisplayName("두 개의 할 일을 비교하여 false를 리턴한다")
            void itReturnsFalse() {
                assertNotEquals(taskA, taskB);
            }
        }

        @Nested
        @DisplayName("만약 id가 다르고 title가 같은 두 개의 할 일이 주어진다면")
        class ContextWithDifferentIdAndSameTitle {
            @BeforeEach
            void prepareTask() {
                taskA = new Task(1L, "same task");
                taskB = new Task(2L, "same task");
            }

            @Test
            @DisplayName("두 개의 할 일을 비교하여 false를 리턴한다")
            void itReturnsTrue() {
                assertNotEquals(taskA, taskB);
            }
        }

        @Nested
        @DisplayName("만약 id가 다르고 title이 다른 두 개의 할 일이 주어진다면")
        class ContextWithDifferentIdAndDifferentTitle {
            @BeforeEach
            void prepareTask() {
                taskA = new Task(1L, "taskA");
                taskB = new Task(2L, "taskB");
            }

            @Test
            @DisplayName("두 개의 할 일을 비교하여 false를 리턴한다")
            void itReturnsFalse() {
                assertNotEquals(taskA, taskB);
            }
        }

        @Nested
        @DisplayName("만약 id가 같고 title이 같은 두 개의 할 일이 주어진다면")
        class ContextWithSameIdAndSameTitle {
            @BeforeEach
            void prepareTask() {
                taskA = new Task(1L, "same task");
                taskB = new Task(1L, "same task");
            }

            @Test
            @DisplayName("두 개의 할 일을 비교하여 true를 리턴한다")
            void itReturnsTrue() {
                assertEquals(taskA, taskB);
            }
        }
    }
}
