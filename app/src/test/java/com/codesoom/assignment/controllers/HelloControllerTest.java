package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("HelloController 클래스")
class HelloControllerTest {

    @Nested
    @DisplayName("sayHello 메서드는")
    class Describe_sayHello {
        @Test
        @DisplayName("\"Hello, World!\"를 String 타입으로 리턴한다.")
        void sayHello() {
            HelloController controller = new HelloController();
            assertThat(controller.sayHello()).isEqualTo("Hello, world!");
        }
    }
}
