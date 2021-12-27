package com.codesoom;

import com.codesoom.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {
    @Test
    void appHasAGreeting() {
        App app = new App();
        assertNotNull(app.getGreeting(), "app should have a greeting");
        assertEquals(app.getGreeting(), "Hello, world!");
    }
}
