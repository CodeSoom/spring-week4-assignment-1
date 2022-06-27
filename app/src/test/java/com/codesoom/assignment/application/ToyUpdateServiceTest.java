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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("ToyUpdateService 클래스")
class ToyUpdateServiceTest {
    private ToyRepository repository;
    private ToyUpdateService service;
    private Toy toy;
    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Toy";
    private final String TOY_NAME_UPDATING = "Updating Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        toy = new Toy(TOY_ID, TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        repository = new InMemoryToyRepository();
        service = new ToyUpdateService(repository);
    }


    @Nested
    @DisplayName("update 메소드는")
    class Describe_update {
        private Toy subject() {
            Toy toyUpdating = new Toy(TOY_ID, TOY_NAME_UPDATING, toy.producer(), toy.price());
            return service.update(TOY_ID, toyUpdating);
        }

        @Nested
        @DisplayName("존재하는 장난감에 대한 Id를 매개변수로 전달하면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                repository.save(toy);
            }

            @Test
            @DisplayName("매개변수로 전달한 Id를 가지고 있는 장난감을 반환한다")
            void it_returns_toy_containing_that_id() {
                final Toy actual = subject();

                assertThat(actual.id()).isEqualTo(TOY_ID);
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
