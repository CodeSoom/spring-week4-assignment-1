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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayName("ToyCrudController 클래스")
class ToyCrudControllerTest {
    @Mock
    private CrudService service;
    private CrudController controller;
    private final List<Product> toys = new LinkedList<>();

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

    private Toy toyTesting() {
        final Producer producer = new ToyProducer("Test Producer");
        final Money money = new Won(new BigDecimal(1000));

        return new Toy("Test Toy", producer, money);
    }
}
