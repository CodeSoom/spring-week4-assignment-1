package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloControllerTest {

    @Test
    void sayHello() {
        HelloController controller = new HelloController();
        String content = controller.sayHello();

        assertThat(content).isEqualTo("Hello, world!");
    }
}
