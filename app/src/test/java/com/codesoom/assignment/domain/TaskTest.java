package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Task 클래스 테스트")
class TaskTest {

    public static final Long EXPECTED_ID = 53L;
    public static final String EXPECTED_TITLE = "CHECK TASK TEST";

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(53L);
        task.setTitle("CHECK TASK TEST");
    }

    @Test
    @DisplayName("특정하게 입력된 값을 올바르게 리턴함.")
    void testTaskDetail() {
        assertThat(task.getId()).isEqualTo(EXPECTED_ID);
        assertThat(task.getTitle()).isEqualTo(EXPECTED_TITLE);
    }

}
