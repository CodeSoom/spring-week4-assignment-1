package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@DisplayName("CatToyService 클래스")
class CatToyServiceTest {
    private CatToyService catToyService;
    private CatToyRepository catToyRepository;

    @BeforeEach
    void setUp() {
        catToyRepository = mock(CatToyRepository.class);
        catToyService = new CatToyService(catToyRepository);
    }

    @Nested
    @DisplayName("getProducts 메소드는")
    class Describe_getProducts {
        @Nested
        @DisplayName("등록된 장남감들이 없다면")
        class Context_no_have_product {
            @Test
            @DisplayName("비어 있는 리스트를 리턴")
            void it_returns_EmptyProducts() {
                List<Product> products = catToyService.getProducts();

                assertThat(products).isEmpty();
            }
        }

        @Nested
        @DisplayName("등록된 장남감들이 있다면")
        class Context_have_product {
            Product product1, product2;

            @BeforeEach
            void PrepareTask() {
                product1 = new Product();
                product2 = new Product();

                List<Product> products = new ArrayList<>();
                products.add(product1);
                products.add(product2);

                given(catToyRepository.findAll()).willReturn(products);
            }

            @Test
            @DisplayName("등록된 장난감들을 리턴")
            void it_returns_products() {
                List<Product> products = catToyService.getProducts();

                verify(catToyRepository).findAll();

                assertThat(products.size()).isEqualTo(2);
                assertThat(products).contains(product1, product2);
            }
        }
    }
}
