package com.codesoom.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {
    private App app;

    @BeforeEach
    void setup() {
        app = new App();
    }
    @Test
    void appHasAGreeting() {
        assertNotNull(app.getGreeting(), "app should have a greeting");
        assertEquals(app.getGreeting(), "Hello, world!");
    }

    @Test
    void appRunsSpringApplication() {
        app.main(new String[] {});
    }
}
