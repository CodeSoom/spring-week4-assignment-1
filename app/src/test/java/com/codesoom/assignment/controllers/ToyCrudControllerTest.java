package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.domain.Won;
import com.codesoom.assignment.interfaces.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToyCrudController 클래스")
class ToyCrudControllerTest {
    @Mock
    private CrudService service;
    private CrudController controller;
    private final List<Product> toys = new LinkedList<>();

    private final Long TOY_ID = 1L;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        controller = new ToyCrudController(service);
    }

    @AfterEach
    void clear() {
        toys.clear();
    }

    @Nested
    @DisplayName("list 메소드는")
    class Describe_list {
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
                final List<Product> actual = controller.list();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 장난감이 존재한다면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                toys.add(toyTesting());
                given(service.showAll()).willReturn(toys);
            }

            @Test
            @DisplayName("비어 있지 않은 List를 반환한다")
            void it_returns_not_empty_list() {
                final List<Product> actual = controller.list();

                assertThat(actual).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("detail 메소드는")
    class Describe_detail {
        @BeforeEach
        void setUp() {
            given(service.showById(TOY_ID)).willReturn(Optional.of(toyTesting()));
        }

        @Test
        @DisplayName("Product Type을 반환한다")
        void it_returns_toy() {
            final Optional<Product> actual = controller.detail(TOY_ID);

            assertThat(actual.get()).isInstanceOf(Product.class);
        }
    }

    private Toy toyTesting() {
        final Producer producer = new ToyProducer(PRODUCER_NAME);
        final Money money = new Won(WON_VALUE);

        return new Toy(TOY_NAME, producer, money);
    }
}
