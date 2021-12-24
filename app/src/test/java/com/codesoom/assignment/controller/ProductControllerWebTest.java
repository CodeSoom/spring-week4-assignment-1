package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerWebTest {
    //    고양이 장난감 목록 얻기 - GET /products
    //    고양이 장난감 상세 조회하기 - GET /products/{id}
    //    고양이 장난감 등록하기 - POST /products
    //    고양이 장난감 수정하기 - PATCH /products/{id}
    //    고양이 장난감 삭제하기 - DELETE /products/{id}

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        product1 = new Product(0L, "catTower", "samsung", 35900L, "https://thumbnail14.coupangcdn.com/thumbnails/remote/712x712ex/image/retail/images/451976858609946-e5186418-5f5e-4f4c-bccc-a59ac573d029.jpg");
        product2 = new Product(1L, "catBall", "love cat", 8000L, "http://mstatic1.e-himart.co.kr/contents/goods/00/05/96/13/20/0005961320__TB10__M_640_640.jpg");
    }

}
