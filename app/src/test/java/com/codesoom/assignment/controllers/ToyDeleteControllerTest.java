package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductDeleteService;
import com.codesoom.assignment.controllers.interfaces.ProductDeleteController;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToyDeleteController 클래스")
class ToyDeleteControllerTest {
    @Mock
    private ProductDeleteService service;
    private ProductDeleteController controller;

    private final Long TOY_ID = 2L;

    @BeforeEach
    void setUp() {
        controller = new ToyDeleteController(service);
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        @Test
        @DisplayName("값을 반환하지 않는다")
        void it_returns_nothing() {
            controller.delete(TOY_ID);
        }
    }

}
