package com.codesoom.assignment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    @DisplayName("스프링 어플리케이션이 가동되는지 확인")
    void application() {
        App.main(new String[] {});
    }
}
