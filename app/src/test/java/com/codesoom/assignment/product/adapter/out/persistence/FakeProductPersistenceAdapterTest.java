package com.codesoom.assignment.product.adapter.out.persistence;

import com.codesoom.assignment.product.application.port.out.FakeCommandProductPort;
import com.codesoom.assignment.product.application.port.out.FakeQueryProductPort;
import com.codesoom.assignment.product.application.port.out.InMemoryProductsGenerator;
import com.codesoom.assignment.product.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static com.codesoom.assignment.support.ProductFieldFixture.TEST_NOT_EXIST;
import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("FakeProductPersistenceAdapter 유닛 테스트")
class FakeProductPersistenceAdapterTest {
    private FakeQueryProductPort fakeQueryProductPort;
    private FakeCommandProductPort fakeCommandProductPort;

    @BeforeEach
    void setUpVariable() {
        InMemoryProductsGenerator productsGenerator = new InMemoryProductsGenerator();
        fakeQueryProductPort = new FakeQueryProductPort(productsGenerator.getProducts());
        fakeCommandProductPort = new FakeCommandProductPort(productsGenerator.getProducts());
    }

    @BeforeEach
    void setUpDeleteAllFixture() {
        fakeCommandProductPort.deleteAllInBatch();
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findAll_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_없을_때 {
            @Test
            @DisplayName("빈 리스트를 리턴한다")
            void it_returns_empty_list() {
                assertThat(fakeQueryProductPort.findAll())
                        .isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 등록된_장난감이_있을_때 {
            @BeforeEach
            void setUpCreate() {
                fakeCommandProductPort.save(TOY_1.생성());
            }

            @Test
            @DisplayName("비어있지 않은 리스트를 리턴한다")
            void it_returns_empty_list() {
                List<Product> products = fakeQueryProductPort.findAll();

                assertThat(products).isNotEmpty();
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class findById_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_없는_id가_주어지면 {
            @Test
            @DisplayName("빈 값을 리턴한다")
            void it_returns_empty_product() {
                assertThat(fakeQueryProductPort.findById(TEST_NOT_EXIST.ID()))
                        .isEmpty();
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 찾을_수_있는_id가_주어지면 {
            @Test
            @DisplayName("해당 id의 장난감 정보를 리턴한다")
            void it_returns_product() {
                Product productSource = fakeCommandProductPort.save(TOY_1.생성());

                Optional<Product> product = fakeQueryProductPort.findById(productSource.getId());

                assertThat(product).isPresent();
                assertThat(product.get().getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.get().getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.get().getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.get().getImgUrl()).isEqualTo(TOY_1.IMAGE());
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class save_메서드는 {

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class null이_주어지면 {
            @Test
            @DisplayName("예외를 던진다")
            void it_returns_exception() {
                assertThatThrownBy(() -> fakeCommandProductPort.save(null))
                        .isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
        class 새로운_상품이_주어지면 {
            @Test
            @DisplayName("상품을 저장하고 리턴한다")
            void it_returns_product() {
                Product product = fakeCommandProductPort.save(TOY_1.생성());

                assertThat(product).isNotNull();
                assertThat(product.getName()).isEqualTo(TOY_1.NAME());
                assertThat(product.getMaker()).isEqualTo(TOY_1.MAKER());
                assertThat(product.getPrice()).isEqualTo(TOY_1.PRICE());
                assertThat(product.getImgUrl()).isEqualTo(TOY_1.IMAGE());
            }

            @Test
            @DisplayName("findAll 메서드 리턴값이 1 증가한다")
            void it_returns_count() {
                int oldSize = fakeQueryProductPort.findAll().size();

                fakeCommandProductPort.save(TOY_1.생성());

                int newSize = fakeQueryProductPort.findAll().size();

                assertThat(newSize - oldSize).isEqualTo(1);
            }
        }
    }

    @Nested
    @DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
    class delete_메서드는 {
        private Product productSource;

        @BeforeEach
        void setUpCreateFixture() {
            productSource = fakeCommandProductPort.save(TOY_1.생성());
        }

        @Test
        @DisplayName("상품을 삭제한다")
        void it_deleted() {
            Long id = productSource.getId();

            fakeCommandProductPort.delete(productSource);

            assertThat(fakeQueryProductPort.findById(id)).isEmpty();
        }
    }
}
