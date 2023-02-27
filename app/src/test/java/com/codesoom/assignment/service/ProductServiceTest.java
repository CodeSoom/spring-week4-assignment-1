package com.codesoom.assignment.service;

import com.codesoom.assignment.common.exception.NotFoundMakerException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ListDto.ProductListDto;
import com.codesoom.assignment.dto.ProductSaveRequestDto;
import com.codesoom.assignment.dto.RequstMakerDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @BeforeEach
    public void registerProduct() {
        //given
        ProductSaveRequestDto product = ProductSaveRequestDto.builder()
                .maker(MAKER)
                .price(1000)
                .img("img")
                .build();
        //when
        Long result = this.productService.registerProduct(product);
        //Then
        assertThat(result).isEqualTo(1L);
    }

    @DisplayName("ddl-auto를 create로 안하고 테스트 사용 , default -> 주석으로 사용")
    @Transactional
    @AfterEach
    public void deleteAll(){
        this.productRepository.deleteAll();
    }

    @Transactional
    @Test
    public void getList() throws Exception {
        //when
        List<ProductListDto> listDtoList = this.productService.getProducts();
//        Then
        assertThat(listDtoList.get(0).getId()).isEqualTo(1L);
        assertThat(listDtoList.get(0).getMaker()).isEqualTo(MAKER);
        assertThat(listDtoList.get(0).getPrice()).isEqualTo(1000);
    }


    @Transactional
    @Test
    public void getProductByMakerValid() throws Exception {
        RequstMakerDto dto = getMakerDto(MAKER);
        //when
        List<Product> findProductByMaker = this.productService.getProductByMaker(dto);
        //Then
        assertThat(findProductByMaker.get(0).getId()).isEqualTo(1L);
        assertThat(findProductByMaker.get(0).getMaker()).isEqualTo(MAKER);
        assertThat(findProductByMaker.get(0).getPrice()).isEqualTo(1000);

    }

    @Transactional
    @Test
    public void getProductByMakerInValid() {
        RequstMakerDto dto = getMakerDto(FAIL_MAKER);

        assertThatExceptionOfType(NotFoundMakerException.class)
                .isThrownBy(() -> {
                    productService.getProductByMaker(dto);
                }).withMessageNotContainingAny("Product Id not found:Fail_Maker");
    }

    @Transactional
    @Test
    public void deleteProductByMakerValid(){
        //given
        RequstMakerDto dto = getMakerDto(MAKER);
        //when
        this.productService.deleteProduct(dto);
        assertThatExceptionOfType(NotFoundMakerException.class)
                .isThrownBy(() -> {
                    productService.getProductByMaker(dto);
                }).withMessageNotContainingAny("Product Id not found:Maker");
    }

    private static RequstMakerDto getMakerDto(String maker) {
        RequstMakerDto dto = RequstMakerDto.builder()
                .maker(maker)
                .build();
        return dto;
    }

}