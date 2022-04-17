package com.codesoom.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
class AppTest {
    @Test
    void appHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull(classUnderTest.getGreeting(), "app should have a greeting");
        assertEquals(classUnderTest.getGreeting(), "Hello, world!");
    }

    @DisplayName("스프링 부트 애플리케이션을 성공적으로 실행한다.")
    @Test
    void springApplicationTest() {
        App.main(new String[]{});
    }

}
