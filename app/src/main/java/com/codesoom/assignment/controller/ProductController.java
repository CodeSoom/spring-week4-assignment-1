package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO        고양이 장난감 목록 얻기 - GET /products - 테스트 미작성, 장난감 등록 후 확인
//TODO        고양이 장난감 상세 조회하기 - GET /products/{id}  - 테스트 미작성, 장난감 등록 후 확인
//TODO        고양이 장난감 등록하기 - POST /products
//TODO        고양이 장난감 수정하기 - PATCH /products/{id}
//TODO        고양이 장난감 삭제하기 - DELETE /products/{id}
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping()
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ProductNotFoundException.class)
    public String productNotFoundExceptionHandler(ProductNotFoundException exception) {
        return exception.getMessage();
    }

}
