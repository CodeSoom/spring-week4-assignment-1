package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@DisplayName("ProductService 클래스의")
class ProductServiceTest {

    private static final String NAME = "name";
    private static final String MAKER = "maker";
    private static final Integer PRICE = 100;
    private static final String IMAGE_URL = "http://localhost:8080/original";
    private static final List<String> CATEGORY_NAME_LIST = Arrays.asList("ball", "doll", "puzzle");

    private static final String UPDATED_NAME = "updated name";
    private static final String UPDATED_MAKER = "updated maker";
    private static final Integer UPDATED_PRICE = 500;
    private static final String UPDATED_IMAGE_URL = "http://www.localhost:8080/updated";
    private static final List<String> UPDATED_CATEGORY_NAME_LIST = Arrays.asList("car", "lego");

    @Autowired
    private ProductService service;

    private Long getExistingId() {
        return service.getProducts().get(0).getId();
    }

    @Test
    @DisplayName("getProducts()는 등록된 제품이 없을 때 빈 리스트를 리턴한다.")
    void given_none_products_registered_when_getProducts_invoked_then_empty_list_returned() {
        List<ProductDto> list = service.getProducts();

        assertThat(list).isEmpty();
    }

    @Test
    @DisplayName("getProducts()는 등록된 제품이 있을 때 비어 있지 않을 리스트를 리턴한다.")
    void given_products_registered_exists_when_getProducts_invoked_then_not_empty_list_returned() {
        service.create(new ProductDto(NAME, MAKER, PRICE, IMAGE_URL, CATEGORY_NAME_LIST));

        List<ProductDto> list = service.getProducts();

        assertThat(list).isNotEmpty();
    }

    @Test
    @DisplayName("getProduct()는 등록된 제품의 id를 인자로 호출하면 해당 제품을 리턴한다.")
    void when_getProduct_invoked_with_registered_id_then_corresponding_product_returned() {
        ProductDto productDto = service.create(new ProductDto(NAME, MAKER, PRICE, IMAGE_URL, CATEGORY_NAME_LIST));

        Long id = productDto.getId();

        assertThat(service.getProduct(id).getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("getProduct()는 등록되지 않은 제품의 id를 인자로 호출하면 예외를 던진다.")
    void when_getProduct_invoked_with_not_registered_id_then_exception_thrown() {
        service.create(new ProductDto(NAME, MAKER, PRICE, IMAGE_URL, CATEGORY_NAME_LIST));

        assertThatThrownBy(() -> service.getProduct(Long.MAX_VALUE))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("create()는 인자로 주어진 product 객체를 repository에 저장한다.")
    void when_create_invoked_product_then_that_product_saved_in_repository() {
        int oldSize = service.getProducts().size();

        service.create(new ProductDto(NAME, MAKER, PRICE, IMAGE_URL, CATEGORY_NAME_LIST));

        int newSize = service.getProducts().size();
        assertThat(newSize - oldSize).isEqualTo(1);
    }

    @Test
    @DisplayName("create()는 고유 id가 부여된 product 객체를 할당한다.")
    void when_create_invoked_then_returns_product_with_unique_id() {
        Set<Long> idSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            ProductDto dto = service.create(new ProductDto(NAME + i, MAKER + i, PRICE + i, IMAGE_URL + i, CATEGORY_NAME_LIST));
            idSet.add(dto.getId());
        }

        assertThat(idSet.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("update()는 존재하는 id를 인자로 호출하면 해당 product 객체의 정보를 수정한다.")
    void when_update_invoked_with_existing_id_then_corresponding_product_is_updated() {
        service.create(new ProductDto(NAME, MAKER, PRICE, IMAGE_URL, CATEGORY_NAME_LIST));

        ProductDto src = new ProductDto(UPDATED_NAME, UPDATED_MAKER, UPDATED_PRICE, UPDATED_IMAGE_URL, UPDATED_CATEGORY_NAME_LIST);
        Long id = getExistingId();
        Product product = service.update(id, src);

        assertThat(product.getName()).isEqualTo(UPDATED_NAME);
        assertThat(product.getMaker()).isEqualTo(UPDATED_MAKER);
        assertThat(product.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(product.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
    }

    @Test
    @DisplayName("update()는 존재하지 않는 id를 인자로 호출하면 예외를 던진다.")
    void when_update_invoked_with_not_existing_id_then_exception_thrown() {
        service.create(new Product(NAME, MAKER, PRICE, IMAGE_URL));

        Product src = new Product(UPDATED_NAME, UPDATED_MAKER, UPDATED_PRICE, UPDATED_IMAGE_URL);
        assertThatThrownBy(() -> service.update(100L, src))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("delete()는 존재하는 id를 인자로 호출하면 해당 product는 삭제된다.")
    void when_update_invoked_with_existing_id_then_corresponding_product_is_deleted() {
        service.create(new Product());

        Long id = getExistingId();

        service.delete(id);

        List<Product> products = service.getProducts();
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("delete()는 존재하지 않는 id를 인자로 호출하면 예외를 던진다.")
    void when_update_invoked_with_not_existing_id_then_corresponding_product_is_deleted() {
        service.create(new Product());

        assertThatThrownBy(() -> service.delete(Long.MAX_VALUE))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
