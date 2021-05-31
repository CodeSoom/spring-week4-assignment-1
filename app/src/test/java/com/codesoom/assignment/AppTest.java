package com.codesoom.assignment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {

    @Test
    void appHasAgreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
        assertEquals(classUnderTest.getGreeting(), "Hello, world!");
    }

    @Test
    void testMain() {
        // 테스트 커버리지 100%를 위해 추가
        App.main(new String[] {"test"});
    }
}
