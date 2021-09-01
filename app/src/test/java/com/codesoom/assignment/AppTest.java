package com.codesoom.assignment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

@DisplayName("App 클래스")
class AppTest {
    @Nested
    @DisplayName("getGreeting 메서드는")
    class Describe_getGreeting {
        private App app;

        public Describe_getGreeting() {
            app = new App();
        }

        @Test
        @DisplayName("App개체가 제대로 작동하고 있음을 알려준다.")
        void it_inform_object_work_fine() {
            assertThat(app.getGreeting()).isNotNull();
        }
    }
    
    @Nested
    @DisplayName("main 메서드는")
    @TestInstance(Lifecycle.PER_CLASS)
    class Describe_main {
        private MockedStatic<SpringApplication> springMock;
        private final String[] args = new String[] {};

        @BeforeAll
        void beforeAll() {
            springMock = mockStatic(SpringApplication.class);
        }

        @BeforeEach
        void setUp() {
            springMock.when(
                () -> SpringApplication.run(App.class, args)
            ).thenReturn(null);
        }

        @Test
        @DisplayName("스프링 어플리케이션을 작동시킨다.")
        void it_runs_a_springApplication() {
            App.main(args);
        }

        @AfterEach
        void tearDown() {
            springMock.verify(
                () -> SpringApplication.run(App.class, args)
            );
        }

        @AfterAll
        void afterAll() {
            springMock.close();
        }
    }
}
