package com.codesoom.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("App 클래스")
@ExtendWith(MockitoExtension.class)
class AppTest {
    @Nested
    @DisplayName("getGreeting 메서드는")
    class Describe_getGreeting {

        @InjectMocks
        App app;

        @Test
        @DisplayName("App개체가 재대로 작동하고 있음을 알려준다.")
        void it_inform_object_work_fine() {
            assertThat(app.getGreeting()).isNotNull();
        }
    }
}
