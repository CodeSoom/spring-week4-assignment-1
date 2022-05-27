package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductDeleteService;
import com.codesoom.assignment.controllers.interfaces.ProductDeleteController;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToyDeleteController 클래스")
class ToyDeleteControllerTest {
    @Mock
    private ProductDeleteService service;
    private ProductDeleteController controller;
    private Toy toy;

    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        toy = new Toy(TOY_ID, TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        controller = new ToyDeleteController(service);
    }

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {
        @BeforeEach
        void setUp() {
            given(service.deleteBy(TOY_ID)).willReturn(toy);
        }

        @Test
        @DisplayName("값을 반환하지 않는다")
        void it_returns_responseDto() {
            controller.delete(TOY_ID);
        }
    }

}
