package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("ToyService test")
class ToyServiceTest {
    private ToyService toyService;

    private static final String TOY_NAME = "test 장난감";
    private static final String TOY_MAKER = "애옹이네 장난감 가게";
    private static final Integer TOY_PRICE = 5000;
    private static final String TOY_IMAGE = "someUrl";
    private static final String CREATE_POSTFIX = "!!!";
    private static final String UPDATE_POSTFIX = "~~~";
    private static final Long NOT_EXISTED_ID = 100L;

    private ToyRepository toyRepository;

    @BeforeEach
    void setUp() {
        toyRepository = mock(ToyRepository.class);
        toyService = new ToyService(toyRepository);

        Toy toy = new Toy(TOY_NAME, TOY_MAKER, TOY_PRICE, TOY_IMAGE);
        given(toyRepository.findById(1L)).willReturn(Optional.of(toy));
        given(toyRepository.findById(NOT_EXISTED_ID))
                .willThrow(new ProductNotFoundException(NOT_EXISTED_ID));
    }

    @Nested
    @DisplayName("Get products")
    class GetProducts {
        @Nested
        @DisplayName("when products existed")
        class whenToysExisted {

            @BeforeEach()
            void setUp() {
                List<Toy> toys = new ArrayList<>();
                Toy toy = new Toy(TOY_NAME, TOY_MAKER, TOY_PRICE, TOY_IMAGE);

                toys.add(toy);
                given(toyRepository.findAll()).willReturn(toys);
            }

            @Test
            @DisplayName("getProducts not empty")
            void getProductsWithExistedToys() {
                List<Toy> toys = toyService.getProducts();

                verify(toyRepository).findAll();

                assertThat(toys).isNotEmpty();
            }
        }

        @Nested
        @DisplayName("when products not existed")
        class whenToysNotExisted {

            @Test
            @DisplayName("getProducts is empty")
            void getProductsWithNotExistedToys() {
                given(toyRepository.findAll()).willReturn(List.of());

                List<Toy> toys = toyService.getProducts();

                verify(toyRepository).findAll();

                assertThat(toys).isEmpty();
            }
        }
    }

    @Nested
    @DisplayName("Get product")
    class GetProduct {

        @Nested
        @DisplayName("when id is existed")
        class whenIdIsExisted {

            @Test
            @DisplayName("return product")
            void getProductWithExistedId() {
                Toy toy = toyService.getProduct(1L);
                assertThat(toy.getName()).isEqualTo(TOY_NAME);

                verify(toyRepository).findById(1L);
            }
        }

        @Nested
        @DisplayName("when id is not existed")
        class whenIdIsNotExisted {

            @Test
            @DisplayName("occur ProductNotFoundException")
            void getProductWithNotExistedId() {
                assertThatThrownBy(() -> toyService.getProduct(NOT_EXISTED_ID))
                    .isInstanceOf(ProductNotFoundException.class);

                verify(toyRepository).findById(NOT_EXISTED_ID);
            }
        }
    }

    @Nested
    @DisplayName("Create product")
    class CreateProduct {

        @BeforeEach
        void setUpSaveToy() {
            given(toyRepository.save(any(Toy.class))).will(invocation -> {
                Toy toy = invocation.getArgument(0);
                toy.setId(2L);
                return toy;
            });
        }

        @Test
        @DisplayName("return created toy")
        void createProduct() {
            Toy source = new Toy(TOY_NAME + CREATE_POSTFIX, TOY_MAKER, TOY_PRICE, TOY_IMAGE);

            Toy toy = toyService.createProduct(source);

            verify(toyRepository).save(any(Toy.class));

            assertThat(toy.getId()).isEqualTo(2L);
            assertThat(toy.getName()).isEqualTo(TOY_NAME + CREATE_POSTFIX);
        }
    }

    @Nested
    @DisplayName("Update product")
    class UpdateProduct {

        @Nested
        @DisplayName("when id is existed")
        class whenIdIsExisted {

            @Test
            @DisplayName("return updated toy")
            void updateProductWithExistedId() {
                Toy source = new Toy(TOY_NAME + UPDATE_POSTFIX, TOY_MAKER, TOY_PRICE, TOY_IMAGE);

                Toy updatedToy = toyService.updateProduct(1L, source);

                verify(toyRepository).findById(1L);

                assertThat(updatedToy.getName()).isEqualTo(TOY_NAME + UPDATE_POSTFIX);
            }
        }

        @Nested
        @DisplayName("when id is not existed")
        class whenIdIsNotExisted {

            @Test
            @DisplayName("occur ProductNotFoundException")
            void updateProductWithNotExistedId() {
                Toy source = new Toy(TOY_NAME + UPDATE_POSTFIX, TOY_MAKER, TOY_PRICE, TOY_IMAGE);

                assertThatThrownBy(() -> toyService.updateProduct(NOT_EXISTED_ID, source))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(toyRepository).findById(NOT_EXISTED_ID);
            }
        }
    }

    @Nested
    @DisplayName("Delete product")
    class DeleteProduct {

        @Nested
        @DisplayName("when id is existed")
        class whenIdIsExisted {

            @Test
            @DisplayName("success delete product")
            void deleteProductWithExistedId() {
                toyService.deleteProduct(1L);

                verify(toyRepository).findById(1L);
                verify(toyRepository).delete(any(Toy.class));
            }
        }

        @Nested
        @DisplayName("when id is not existed")
        class whenIdIsNotExisted {

            @Test
            @DisplayName("occur ProductNotFoundException")
            void deleteProductWithNotExistedId() {
                assertThatThrownBy(() -> toyService.deleteProduct(NOT_EXISTED_ID))
                        .isInstanceOf(ProductNotFoundException.class);

                verify(toyRepository).findById(NOT_EXISTED_ID);
            }
        }
    }
}