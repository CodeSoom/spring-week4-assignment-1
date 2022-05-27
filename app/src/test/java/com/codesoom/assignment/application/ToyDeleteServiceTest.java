package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.ProductNotFoundException;
import com.codesoom.assignment.domain.InMemoryToyRepository;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.domain.interfaces.ToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("ToyDeleteService 클래스")
class ToyDeleteServiceTest {
    private ToyRepository repository;
    private ToyDeleteService service;
    private Toy toy;
    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        toy = new Toy(TOY_ID, TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        repository = new InMemoryToyRepository();
        service = new ToyDeleteService(repository);
    }

    @Nested
    @DisplayName("deleteBy 메소드는")
    class Describe_deleteBy {
        private void subject() {
            service.deleteBy(TOY_ID);
        }

        @Nested
        @DisplayName("존재하는 장난감에 대한 Id를 매개변수로 전달하면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                repository.save(toy);
            }

            @Test
            @DisplayName("아무것도 반환하지 않는다")
            void it_returns_nothing() {
                subject();
            }
        }

        @Nested
        @DisplayName("존재하지 않는 장난감에 대한 Id를 매개변수로 전달하면")
        class Context_without_existing_toy {
            @Test
            @DisplayName("예외를 발생시킨다")
            void it_throws_exception() {
                assertThatThrownBy(() -> subject())
                        .isInstanceOf(ProductNotFoundException.class);
            }
        }
    }
}
