package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.interfaces.ProductCrudService;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;
import com.codesoom.assignment.controllers.interfaces.ProductListController;
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
@DisplayName("ToyListController 클래스")
class ToyListControllerTest {
    @Mock
    private ProductCrudService service;
    private ProductListController controller;
    private Toy toy;
    private final List<Toy> toys = new LinkedList<>();

    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        toy = new Toy(TOY_ID, TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        controller = new ToyCrudController(service);
    }

    @AfterEach
    void clear() {
        toys.clear();
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_list {
        private List<ToyResponseDto> subject() {
            return controller.list();
        }

        @Nested
        @DisplayName("만약 장난감이 존재하지 않는다면")
        class Context_without_existing_toy {

            @BeforeEach
            void setUp() {
                toys.clear();
                given(service.showAll()).willReturn(toys);
            }

            @Test
            @DisplayName("비어 있는 List를 반환한다")
            void it_returns_empty_list() {
                final List<ToyResponseDto> actual = subject();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 장난감이 존재한다면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                toys.add(toy);
                given(service.showAll()).willReturn(toys);
            }

            @Test
            @DisplayName("비어 있지 않은 List를 반환한다")
            void it_returns_not_empty_list() {
                final List<ToyResponseDto> actual = subject();

                assertThat(actual).isNotEmpty();
            }
        }
    }
}
