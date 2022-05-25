package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.InMemoryToyRepository;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.interfaces.ProductCrudService;
import com.codesoom.assignment.interfaces.ToyRepository;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ToyProductCrudService 클래스")
class ToyProductCrudServiceTest {
    private ToyRepository repository;
    private ProductCrudService service;
    private List<Toy> toys;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        repository = new InMemoryToyRepository();
        service = new ToyProductCrudService(repository);
    }

    @Nested
    @DisplayName("showAll 메소드는")
    class Describe_showAll {
        @Nested
        @DisplayName("만약 장난감이 존재하지 않는다면")
        class Context_without_existing_toy {
            @Test
            @DisplayName("비어 있는 List를 반환한다")
            void it_returns_empty_list() {
                final List<Toy> actual = service.showAll();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 장난감이 존재한다면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                repository.save(new Toy(TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE));
            }

            @Test
            @DisplayName("비어 있지 않은 List를 반환한다")
            void it_returns_not_empty_list() {
                final List<Toy> actual = service.showAll();

                assertThat(actual).isNotEmpty();
            }
        }
    }
}
