package com.codesoom.assignment.controllers;

import com.codesoom.assignment.dto.CatToyDTO;
import com.codesoom.assignment.exceptions.ToyNotFoundException;
import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import com.codesoom.assignment.repositories.ToyRepository;
import com.codesoom.assignment.services.CatToyService;
import com.codesoom.assignment.services.ToyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductsControllerTest {
    private ToyService catToyService;
    private ProductsController productsController;

    @BeforeEach
    void setup() {
        final ToyRepository toyRepository = new ToyRepository.Fake();
        this.catToyService = new CatToyService(toyRepository);
        this.productsController = new ProductsController(catToyService);
    }

    private final Toy givenToy1 = new CatToy(0L, "cat nip", "cat company. co", 1000D, "https://cat.toy/cat-nip.png");
    private final Toy givenToy2 = new CatToy(1L, "cat tower", "cat company. co", 10000D, "https://cat.toy/cat-tower.png");

    private final CatToyDTO givenToyDTO1 = new CatToyDTO(givenToy1);
    private final CatToyDTO givenToyDTO2 = new CatToyDTO(givenToy2);

    private boolean equalsDTOAndEntity(CatToyDTO toyDTO, Toy toy) {
        return toyDTO.name().equals(toy.name())
                && toyDTO.maker().equals(toy.brand())
                && toyDTO.price().equals(toy.price())
                && toyDTO.imageUrl().equals(toy.imageURL());
    }

    @Nested
    @DisplayName("getAllProducts 메서드는")
    class Describe_getAllProducts {
        @BeforeEach
        void setup() {
            catToyService.insert(givenToy1);
            catToyService.insert(givenToy2);
        }

        @Test
        @DisplayName("모든 장난감들을 리턴한다.")
        void It_returns_all_toys() {
            List<CatToyDTO> toys = productsController.getAllProducts();

            assertThat(toys).hasSize(2);
        }
    }

    @Nested
    @DisplayName("getProduct 메서드는")
    class Describe_getProduct {
        @Nested
        @DisplayName("주어진 id의 장난감을 찾을 수 없을 때")
        class Context_when_not_exist_toy_id {
            @Test
            @DisplayName("장난감이 없다는 예외를 던진다.")
            void It_throws_toy_not_found_exception() {
                assertThatThrownBy(() -> productsController.getProduct(givenToy1.id()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때")
        class Context_when_exist_toy_id {
            @BeforeEach
            void setup() {
                catToyService.insert(givenToy1);
            }

            @Test
            @DisplayName("장난감을 리턴한다.")
            void It_returns_toy() {
                CatToyDTO toyDTO = productsController.getProduct(givenToy1.id());

                assertThat(equalsDTOAndEntity(toyDTO, givenToy1)).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("createProduct 메서드는")
    class Describe_createProduct {
        @Test
        @DisplayName("주어진 장난감을 저장한다.")
        void It_save_given_toy() {
            assertThatThrownBy(() -> productsController.getProduct(givenToy1.id()))
                    .isInstanceOf(ToyNotFoundException.class);

            productsController.createProduct(givenToyDTO1);
            CatToyDTO toyDTO = productsController.getProduct(givenToy1.id());

            assertThat(equalsDTOAndEntity(toyDTO, givenToy1)).isTrue();
        }
    }

    @Nested
    @DisplayName("updateProduct 메서드는")
    class Describe_updateProduct {
        private final Toy modifiedToy1 = new CatToy(givenToy1.id(), "mattattabi stick", givenToy1.brand(), 2000D, "https://cat.toy/maddaddabi-stick.png");
        private final CatToyDTO modifiedToyDTO1 = new CatToyDTO(modifiedToy1);

        @Nested
        @DisplayName("주어진 id의 장난감을 찾을 수 없을 때")
        class Context_when_not_exist_toy_id {
            @Test
            @DisplayName("장난감이 없다는 예외를 던진다.")
            void It_throws_toy_not_found_exception() {
                assertThatThrownBy(() -> productsController.updateProduct(givenToy1.id(), modifiedToyDTO1))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때")
        class Context_when_exist_toy_id {
            @BeforeEach
            void setup() {
                catToyService.insert(givenToy1);
            }

            @Test
            @DisplayName("장난감의 정보를 변경한다.")
            void It_returns_toy() {
                productsController.updateProduct(givenToy1.id(), modifiedToyDTO1);

                CatToyDTO toyDTO = productsController.getProduct(givenToy1.id());

                assertThat(equalsDTOAndEntity(toyDTO, modifiedToy1)).isTrue();
            }
        }
    }

    @Nested
    @DisplayName("deleteProduct 메서드는")
    class Describe_deleteProduct {
        @Nested
        @DisplayName("주어진 id의 장난감을 찾을 수 없을 때")
        class Context_when_not_exist_toy_id {
            @Test
            @DisplayName("장난감이 없다는 예외를 던진다.")
            void It_throws_toy_not_found_exception() {
                assertThatThrownBy(() -> productsController.deleteProduct(givenToy1.id()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }

        @Nested
        @DisplayName("주어진 id의 장난감이 있을 때")
        class Context_when_exist_toy_id {
            @BeforeEach
            void setup() {
                catToyService.insert(givenToy1);
            }

            @Test
            @DisplayName("주어진 id의 장난감을 삭제한다.")
            void It_delete_given_id_toy() {
                productsController.deleteProduct(givenToy1.id());

                assertThatThrownBy(() -> productsController.getProduct(givenToy1.id()))
                        .isInstanceOf(ToyNotFoundException.class);
            }
        }
    }
}
