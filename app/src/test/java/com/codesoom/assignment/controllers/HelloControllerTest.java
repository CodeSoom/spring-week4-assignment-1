package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DisplayName("HelloController 클래스")
class HelloControllerTest {
    private HelloController helloController;

    @BeforeEach
    void setUp() {
        helloController = new HelloController();
    }

    @Nested
    @DisplayName("sayHello 메서드는")
    class ContextWithNone {
        @Test
        @DisplayName("Hello, world!를 반환한다")
        void itReturnsWithHelloWorldString() {
            String result = helloController.sayHello();

            assertThat(result).isEqualTo("Hello, world!");
        }
    }
}
