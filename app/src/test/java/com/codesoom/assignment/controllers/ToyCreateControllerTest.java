package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductCrudService;
import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductCrudController;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToyCreateController 클래스")
class ToyCreateControllerTest {
    @Mock
    private ProductCrudService service;
    private ProductCrudController controller;
    private Toy toy;
    private Toy toyWithoutId;
    private final List<Toy> toys = new LinkedList<>();

    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        toy = new Toy(TOY_ID, TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        toyWithoutId = new Toy(TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        controller = new ToyCrudController(service);
    }

    @AfterEach
    void clear() {
        toys.clear();
    }


    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        private ToyResponseDto subject() {
            ToyRequestDto requestDto = new ToyRequestDto(TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
            return controller.create(requestDto);
        }

        @BeforeEach
        void setUp() {
            given(service.create(toyWithoutId)).willReturn(toy);
        }

        @Test
        @DisplayName("ToyResponseDto 형태로 값을 반환한다")
        void it_returns_responseDto() {
            final ToyResponseDto actual = subject();

            assertThat(actual).isInstanceOf(ToyResponseDto.class);
        }
    }
}
