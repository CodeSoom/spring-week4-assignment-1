package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundIdException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.dto.RequstIdDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;


@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;


    static final String MAKER = "MAKER";
    static final String FAIL_MAKER = "Fail_Maker";
    static final Long SUCCESS_ID = 1L;
    static final Long FAIL_ID = 100L;
    static final int DUMMY_PRICE = 1000;

    static final String UPDATE_NAME = "UPDATE_NAME";
    static final String UPDATE_MAKER = "UPDATE_MAKER";
    static final int UPDATE_PRICE = 99999;
    static final String UPDATE_IMG = "UPDATE_IMG";

    @BeforeEach
    public void registerProduct() {
        Long result = this.productService.registerProduct(ProductSaveRequestDto.builder()
                .maker(MAKER)
                .price(DUMMY_PRICE)
                .img("img")
                .build());

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
        assertAll(
                () -> assertThat(this.productService.getProducts().get(0).getId()).isEqualTo(SUCCESS_ID),
                () -> assertThat(this.productService.getProducts().get(0).getMaker()).isEqualTo(MAKER),
                () -> assertThat(this.productService.getProducts().get(0).getPrice()).isEqualTo(DUMMY_PRICE)
        );
    }

    @Test
    @DisplayName("update 로직 성공")
    public void updateValid() throws Exception{
        //given
        List<ProductDto> products = productService.getProducts();
        //when
        ProductDto build = ProductDto.builder()
                .name(UPDATE_NAME)
                .maker(UPDATE_MAKER)
                .price(UPDATE_PRICE)
                .img(UPDATE_IMG)
                .build();
        productService.modifyProduct(1L ,build);

        assertThat(productService.getProducts().get(0).getMaker()).isEqualTo(UPDATE_MAKER);
        
        //Then
    }


    @Transactional
    @Test
    public void getProductByIdValid() throws Exception {
        assertAll(
                () -> assertThat(this.productService.getProductById(SUCCESS_ID).getId()).isEqualTo(SUCCESS_ID),
                () -> assertThat(this.productService.getProductById(SUCCESS_ID).getMaker()).isEqualTo(MAKER),
                () -> assertThat(this.productService.getProductById(SUCCESS_ID).getPrice()).isEqualTo(DUMMY_PRICE)
        );
    }

    @Transactional
    @Test
    public void getProductByMakerInValid() {
        RequstIdDto dto = getRequstIdDto(FAIL_ID);

        assertThatExceptionOfType(NotFoundIdException.class)
                .isThrownBy(() -> {
                    productService.getProductById(FAIL_ID);
                }).withMessageNotContainingAny("Product Id not found:" + FAIL_ID);
    }


    @Transactional
    @Test
    public void deleteProductByMakerValid() {
        this.productService.deleteProduct(SUCCESS_ID);
        assertThatExceptionOfType(NotFoundIdException.class)
                .isThrownBy(() -> {
                    productService.getProductById(FAIL_ID);
                }).withMessageNotContainingAny("Product Id not found:" + FAIL_ID);
    }


    private static RequstIdDto getRequstIdDto(Long id) {
        return RequstIdDto.builder()
                .id(id)
                .build();
    }
}
