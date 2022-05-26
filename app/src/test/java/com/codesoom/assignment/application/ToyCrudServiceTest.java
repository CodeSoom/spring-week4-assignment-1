package com.codesoom.assignment.application;

import com.codesoom.assignment.application.exceptions.ProductNotFoundException;
import com.codesoom.assignment.domain.InMemoryToyRepository;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyProducer;
import com.codesoom.assignment.interfaces.ProductCrudService;
import com.codesoom.assignment.interfaces.ToyRepository;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("ToyProductCrudService 클래스")
class ToyCrudServiceTest {
    private ToyRepository repository;
    private ProductCrudService service;
    private Toy toy;
    private final Long TOY_ID = 2L;
    private final String TOY_NAME = "Test Toy";
    private final String PRODUCER_NAME = "Test Producer";
    private final BigDecimal WON_VALUE = new BigDecimal(1000);

    @BeforeEach
    void setUp() {
        toy = new Toy(TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
        repository = new InMemoryToyRepository();
        service = new ToyCrudService(repository);
    }

    @Nested
    @DisplayName("showAll 메소드는")
    class Describe_showAll {
        private List<Toy> subject() {
            return service.showAll();
        }

        @Nested
        @DisplayName("만약 장난감이 존재하지 않는다면")
        class Context_without_existing_toy {
            @Test
            @DisplayName("비어 있는 List를 반환한다")
            void it_returns_empty_list() {
                final List<Toy> actual = subject();

                assertThat(actual).isEmpty();
            }
        }

        @Nested
        @DisplayName("만약 장난감이 존재한다면")
        class Context_with_existing_toy {
            @BeforeEach
            void setUp() {
                repository.save(toy);
            }

            @Test
            @DisplayName("비어 있지 않은 List를 반환한다")
            void it_returns_not_empty_list() {
                final List<Toy> actual = subject();

                assertThat(actual).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayName("showById 메소드는")
    class Describe_showById {
        private Toy subject() {
            return service.showById(TOY_ID);
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

    @Nested
    @DisplayName("create 메소드는")
    class Describe_create {
        private Toy subject() {
            Toy toyWithoutId = toyTestingWithoutId();
            return service.create(toyWithoutId);
        }

        @Test
        @DisplayName("Id가 자동 생성된 Toy를 반환한다")
        void it_returns_toy_with_generated_id() {
            final Toy actual = subject();

            assertThat(actual.id()).isNotNull();
        }
    }


    private Toy toyTestingWithoutId() {
        return new Toy(TOY_NAME, new ToyProducer(PRODUCER_NAME), WON_VALUE);
    }
}
