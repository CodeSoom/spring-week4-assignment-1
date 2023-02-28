package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundMakerException;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.dto.RequstIdDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;


@SpringBootTest
@Transactional(readOnly = true)
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    private static final String MAKER = "MAKER";
    private static final String FAIL_MAKER = "Fail_Maker";
    private static final Long SUCCESS_ID = 1L;
    private static final Long FAIL_ID = 100L;
    private static final int DUMMY_PRICE = 1000;

    @BeforeEach
    public void registerProduct() {
        //given
        //when
        Long result = this.productService.registerProduct(ProductSaveRequestDto.builder()
                .maker(MAKER)
                .price(DUMMY_PRICE)
                .img("img")
                .build());
        //Then
        assertThat(result).isNotNull();
    }

    @DisplayName("ddl-auto를 create로 안하고 테스트 사용 , default -> 주석으로 사용")
    @Transactional
    @AfterEach
    public void deleteAll() {
        this.productRepository.deleteAll();
    }

    @Transactional
    @Test
    public void getList() throws Exception {
        assertThat(this.productService.getProducts().get(0).getId()).isEqualTo(SUCCESS_ID);
        assertThat(this.productService.getProducts().get(0).getMaker()).isEqualTo(MAKER);
        assertThat(this.productService.getProducts().get(0).getPrice()).isEqualTo(DUMMY_PRICE);
    }


    @Transactional
    @Test
    public void getProductByIdValid() throws Exception {
        RequstIdDto dto = getRequstIdDto(SUCCESS_ID);
        assertThat(this.productService.getProductById(dto).get(0).getId()).isEqualTo(SUCCESS_ID);
        assertThat(this.productService.getProductById(dto).get(0).getMaker()).isEqualTo(MAKER);
        assertThat(this.productService.getProductById(dto).get(0).getPrice()).isEqualTo(DUMMY_PRICE);

    }

    @Transactional
    @Test
    public void getProductByMakerInValid() {
        RequstIdDto dto = getRequstIdDto(FAIL_ID);

        assertThatExceptionOfType(NotFoundMakerException.class)
                .isThrownBy(() -> {
                    productService.getProductById(dto);
                }).withMessageNotContainingAny("Product Id not found:" + FAIL_ID);
    }

    @Transactional
    @Test
    public void deleteProductByMakerValid() {
        this.productService.deleteProduct(getRequstIdDto(SUCCESS_ID));
        assertThatExceptionOfType(NotFoundMakerException.class)
                .isThrownBy(() -> {
                    productService.getProductById(getRequstIdDto(FAIL_ID));
                }).withMessageNotContainingAny("Product Id not found:" + FAIL_ID);
    }


    private static RequstIdDto getRequstIdDto(Long id) {
        return RequstIdDto.builder()
                .id(id)
                .build();
    }
}
