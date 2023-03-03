package com.codesoom.assignment.controller;


import com.codesoom.assignment.domain.repository.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        List<ProductDto> list = new ArrayList<>();
        ProductDto product = getProductDto();
        list.add(product);
        given(productService.getProducts()).willReturn(list);
    }

    private  ProductDto getProductDto() {
        ProductDto product = new ProductDto();
        product.setId(1L);
        product.setName("name");
        product.setMaker("maker");
        product.setPrice(1000);
        product.setImg("img");
        return product;
    }

    @Test
    @DisplayName("/get")
    public void URL_GET() throws Exception {
        ProductDto product = getProductDto();

        String JSON = objectMapper.writeValueAsString(product);

        //when
        mockMvc.perform(get("/products")
                        .contentType(APPLICATION_JSON)
                        .content(JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("["+JSON+"]"))
                .andDo(print());

        //Then
        verify(productService).getProducts();
    }


    @Test
    @DisplayName("GET_ID 성공 테스트")
    public void URL_GET_ID() throws Exception{
        //given
        ProductDto product = getProductDto();

        given(productService.getProductById(1L)).willReturn(product);

        String JSON = objectMapper.writeValueAsString(product);

        mockMvc.perform(get("/products/{id}",product.getId())
                        .contentType(APPLICATION_JSON)
                        .content(JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(JSON))
                .andDo(print());
        //Then
        verify(productService).getProductById(anyLong());
    }

    @Test
    @DisplayName("delete_성공 테스트")
    public void URL_DELETE_ID() throws Exception{
        //given
        ProductDto product = getProductDto();

        given(productService.getProductById(1L)).willReturn(product);
        //when
        System.out.println("product = " + product.getId());
        mockMvc.perform(delete("/products/{id}",product.getId()))
                .andExpect(status().isOk());
        //Then
        verify(productService).deleteProduct(1L);

        assertThat(productService.getProducts()).isNotNull();
    }

    @Test
    @DisplayName("update 성공 테스트")
    public void updateValid() throws Exception{
        //given
        Long productId = 1L;
        ProductDto product = getProductDto();

        ProductDto updateProduct = new ProductDto();
        updateProduct.setId(1L);
        updateProduct.setName("update");
        updateProduct.setMaker("updatemaker");
        updateProduct.setPrice(22);
        updateProduct.setImg("updateIMG");


        //when
        mockMvc.perform(patch("/products/" + productId)
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateProduct)))
                .andExpect(status().isOk())
                .andDo(print());

    }


    @Test
    @DisplayName("delte_실패 테스트")
    public void deleteInValid() throws Exception{
        //given
        ProductDto product = getProductDto();
        given(productService.getProductById(1L)).willReturn(product);
        //when
        mockMvc.perform(delete("/products/100"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(contains("Product Id not found:100")))
                .andDo(print());
        //Then
        verify(productService).deleteProduct(100L);
    }

}