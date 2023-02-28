package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundIdException;
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

    private static final String UPDATE_NAME = "UPDATE_NAME";
    private static final String UPDATE_MAKER = "UPDATE_MAKER";
    private static final int UPDATE_PRICE = 99999;
    private static final String UPDATE_IMG = "UPDATE_IMG";

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

        assertThatExceptionOfType(NotFoundIdException.class)
                .isThrownBy(() -> {
                    productService.getProductById(dto);
                }).withMessageNotContainingAny("Product Id not found:" + FAIL_ID);
    }

    @Transactional
    @Test
    public void test() throws Exception {
        //given
        RequstIdDto dto = RequstIdDto.builder()
                .id(1L).build();
        productService.getProductById(dto).forEach(i -> System.out.println("i = " + i.getId()));

        //when
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .name(UPDATE_NAME)
                .maker(UPDATE_MAKER)
                .price(UPDATE_PRICE)
                .img(UPDATE_IMG)
                .build();

        productService.modifyProduct(productDto);
        productService.getProducts().forEach(i -> System.out.println("=============== = " + i));

        //Then
        assertThat(productService.getProductById(dto).get(0).getId()).isEqualTo(1L);
        assertThat(productService.getProductById(dto).get(0).getName()).isEqualTo(UPDATE_NAME);
        assertThat(productService.getProductById(dto).get(0).getMaker()).isEqualTo(UPDATE_MAKER);
        assertThat(productService.getProductById(dto).get(0).getPrice()).isEqualTo(UPDATE_PRICE);
        assertThat(productService.getProductById(dto).get(0).getImg()).isEqualTo(UPDATE_IMG);
    }

    @Transactional
    @Test
    public void deleteProductByMakerValid() {
        this.productService.deleteProduct(getRequstIdDto(SUCCESS_ID));
        assertThatExceptionOfType(NotFoundIdException.class)
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
