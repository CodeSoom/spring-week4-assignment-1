package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.EnableMockMvc;
import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.exception.ProductInvalidPriceException;
import com.codesoom.assignment.product.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static com.codesoom.assignment.Constant.IMAGE_URL;
import static com.codesoom.assignment.Constant.MAKER;
import static com.codesoom.assignment.Constant.NAME;
import static com.codesoom.assignment.Constant.OTHER_IMAGE_URL;
import static com.codesoom.assignment.Constant.OTHER_MAKER;
import static com.codesoom.assignment.Constant.OTHER_NAME;
import static com.codesoom.assignment.Constant.OTHER_PRICE;
import static com.codesoom.assignment.Constant.PRICE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProductController API Web 테스트")
@SpringBootTest
@EnableMockMvc
class ProductControllerWebTest {


    private static final Long MINUS_PRICE = -3000L;

    private static final String API_PATH = "/products";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper objectMapper;

    private Product product;

    private Product productWithInvalidPrice;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        setUpFixture();
    }

    private void setUpFixture() {
        product = new Product(1L, NAME, MAKER, PRICE, IMAGE_URL);

        productWithInvalidPrice = new Product(NAME, MAKER, PRICE, IMAGE_URL);
        ReflectionTestUtils.setField(productWithInvalidPrice, "price", MINUS_PRICE);


        given(productService.findAll()).willReturn(new ArrayList<>());

        given(productService.save(product)).willReturn(product);

        given(productService.save(productWithInvalidPrice))
                .willThrow(new ProductInvalidPriceException(MINUS_PRICE));

        given(productService.findById(1L))
                .willReturn(product)
                .willReturn(product)
                .willThrow(new ProductNotFoundException(1L));

        given(productService.findById(100L))
                .willThrow(new ProductNotFoundException(100L));

        given(productService.updateProduct(eq(1L), any(Product.class)))
                .willReturn(Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL));

        given(productService.updateProduct(eq(100L), any(Product.class)))
                .willThrow(new ProductNotFoundException(100L));
    }


    @DisplayName("상품 목록을 얻을 수 있습니다 - GET /products")
    @Test
    void getProducts() throws Exception {
        mockMvc.perform(get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @DisplayName("식별자를 통해 상품 상세조회를 할 수 있습니다. - GET /products/{id}")
    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get(API_PATH + "/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.maker").value(product.getMaker()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()));
    }

    @DisplayName("존재하지 않는 식별자로 상품을 조회할 경우 NOT_FOUND를 반환합니다. - GET /products/{notExistsId}")
    @Test
    void getProductByNotExistsId() throws Exception {
        mockMvc.perform(get(API_PATH + "/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(ProductNotFoundException.DEFAULT_MESSAGE, 100L)));

    }

    @DisplayName("상품을 등록할 수 있습니다. - POST /products")
    @Test
    void createProduct() throws Exception {
        final String jsonProduct = objectMapper.writeValueAsString(product);

        mockMvc.perform(
                        post(API_PATH)
                                .content(jsonProduct)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.maker").value(product.getMaker()))
                .andExpect(jsonPath("$.price").value(product.getPrice()))
                .andExpect(jsonPath("$.imageUrl").value(product.getImageUrl()));
    }

    @DisplayName("잘못 된 정보로 상품을 등록할 수 없습니다 - POST /products")
    @Test
    void createProductInvalidPrice() throws Exception {
        final String jsonProduct = objectMapper.writeValueAsString(productWithInvalidPrice);

        mockMvc.perform(
                        post(API_PATH)
                                .content(jsonProduct)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value(String.format(ProductInvalidPriceException.DEFAULT_MESSAGE, MINUS_PRICE)));

    }

    @DisplayName("식별자를 이용해 상품 정보를 수정할 수 있습니다 - PATCH /products/{id}")
    @Test
    void updateProduct() throws Exception {
        final Product otherProduct = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        final String jsonOtherProduct = objectMapper.writeValueAsString(otherProduct);

        mockMvc.perform(
                        patch(API_PATH + "/1")
                                .content(jsonOtherProduct)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(otherProduct.getName()))
                .andExpect(jsonPath("$.maker").value(otherProduct.getMaker()))
                .andExpect(jsonPath("$.price").value(otherProduct.getPrice()))
                .andExpect(jsonPath("$.imageUrl").value(otherProduct.getImageUrl()));

    }

    @DisplayName("존재하지 않는 식별자의 상품 정보를 수정하려 할 경우 NOT_FOUND로 실패합니다 - PATCH /products/{notExistsId}")
    @Test
    void updateProductNotExistsId() throws Exception {
        final Product otherProduct = Product.of(OTHER_NAME, OTHER_MAKER, OTHER_PRICE, OTHER_IMAGE_URL);
        final String jsonOtherProduct = objectMapper.writeValueAsString(otherProduct);

        mockMvc.perform(
                        patch(API_PATH + "/100")
                                .content(jsonOtherProduct)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(ProductNotFoundException.DEFAULT_MESSAGE, 100L)));

    }

    @DisplayName("식별자를 이용해 상품 정보를 삭제할 수 있습니다 - DELETE /product/{id}")
    @Test
    void deleteProduct() throws Exception {
        mockMvc.perform(get(API_PATH + "/" + product.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(delete(API_PATH +"/1"))
                .andExpect(status().isNoContent());

        mockMvc.perform(get(API_PATH + "/" + product.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(ProductNotFoundException.DEFAULT_MESSAGE, 1L)));
    }

    @DisplayName("존재하지 않는 식별자의 상품 정보를 삭제하려 할 경우 NOT_FOUND로 실패합니다. - DELETE /product/{notExistsId}")
    @Test
    void deleteProductNotExistsId() throws Exception {
        mockMvc.perform(delete(API_PATH +"/100"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value(String.format(ProductNotFoundException.DEFAULT_MESSAGE, 100L)));
    }

}
