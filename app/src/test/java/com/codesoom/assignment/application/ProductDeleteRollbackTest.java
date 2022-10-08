package com.codesoom.assignment.application;

import com.codesoom.assignment.controller.dto.ProductRequestDto;
import com.codesoom.assignment.controller.dto.ProductResponseDto;
import com.codesoom.assignment.controller.dto.ProductUpdateRequest;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@DisplayName("ProductCommandService 삭제 메소드 롤백 테스트")
public class ProductDeleteRollbackTest {

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private ProductQueryService productQueryService;

    private ProductUpdateRequest getProductRequest() {
        final String name = "my toy";
        final String maker = "my shop";
        final int price = 10000;
        final String imageUrl = "toy.jpg";

        return new ProductRequestDto(name, maker, price, imageUrl);
    }

    @Nested
    @DisplayName("deleteByIds 메소드는")
    class Describe_deleteByIds {

        @Nested
        @DisplayName("삭제 불가능한 아이디가 하나라도 주어지면")
        class Context_with_exception_thrown {
            private final Set<Long> ids = new LinkedHashSet<>();

            @BeforeEach
            void setUp() {
                int idSize = 5;

                for (int i = 0; i < idSize; i++) {
                    ProductResponseDto product = productCommandService.create(getProductRequest());
                    ids.add(product.getId());
                }

                Long lastAddedId = new ArrayList<>(ids).get(idSize - 1);
                productCommandService.deleteById(lastAddedId);
            }

            @Test
            @DisplayName("예외를 던지고 수행했던 삭제 작업을 모두 롤백한다")
            void it_does_rollback() {
                List<ProductResponseDto> beforeDelete = productQueryService.getAll();

                assertThatThrownBy(() -> productCommandService.deleteByIds(ids))
                        .isInstanceOf(ProductNotFoundException.class);

                List<ProductResponseDto> afterDelete = productQueryService.getAll();

                assertThat(beforeDelete.size()).isEqualTo(afterDelete.size());
            }
        }
    }
}
