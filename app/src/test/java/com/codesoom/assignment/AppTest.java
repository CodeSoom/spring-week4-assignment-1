package com.codesoom.assignment;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@WebAppConfiguration
class AppTest {
    @Test
    void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
        assertEquals(classUnderTest.getGreeting(), "Hello, world!");
    }

    @Test
    public void applicationStarts() {
        String[] args = new String[0];
        App.main(args);
        assertTrue(true);
    }
}
