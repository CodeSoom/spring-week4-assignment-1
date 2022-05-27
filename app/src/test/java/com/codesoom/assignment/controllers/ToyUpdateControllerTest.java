package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductUpdateService;
import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductUpdateController;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToyUpdateController 클래스")
class ToyUpdateControllerTest {
    @Mock
    private ProductUpdateService service;
    private ProductUpdateController controller;
    private ToyRequestDto requestDto;

    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Test Toy";
    private final String TOY_NAME_UPDATING = "Updated Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        requestDto = new ToyRequestDto(TOY_NAME_UPDATING, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        controller = new ToyUpdateController(service);
    }


    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        private ToyResponseDto subject() {
            return controller.update(TOY_ID, requestDto);
        }

        @BeforeEach
        void setUp() {
            Toy toyUpdating = new Toy(TOY_ID, TOY_NAME_UPDATING, new ToyProducer(PRODUCER_NAME), WON_VALUE);
            given(service.update(TOY_ID, requestDto.toEntity())).willReturn(toyUpdating);
        }

        @Test
        @DisplayName("ToyResponseDto 형태로 값을 반환한다")
        void it_returns_responseDto() {
            final ToyResponseDto actual = subject();

            assertThat(actual).isInstanceOf(ToyResponseDto.class);
        }
    }
}
