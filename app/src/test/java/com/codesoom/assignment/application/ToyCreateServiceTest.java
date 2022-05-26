package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.InMemoryToyRepository;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.domain.interfaces.ToyRepository;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ToyCreateService 클래스")
class ToyCreateServiceTest {
    private ToyRepository repository;
    private ToyCreateService service;
    private Toy toyWithoutId;
    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        toyWithoutId = new Toy(TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        repository = new InMemoryToyRepository();
        service = new ToyCreateService(repository);
    }

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        private Toy subject() {
            return service.create(toyWithoutId);
        }

        @Test
        @DisplayName("Id가 자동 생성된 Toy를 반환한다")
        void it_returns_toy_with_generated_id() {
            final Toy actual = subject();

            assertThat(actual.id()).isNotNull();
        }
    }

}
