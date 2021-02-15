package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO        고양이 장난감 목록 얻기 - GET /products - 테스트 미작성, 장난감 등록 후 확인
//TODO        고양이 장난감 상세 조회하기 - GET /products/{id}  - 테스트 미작성, 장난감 등록 후 확인
//TODO        고양이 장난감 등록하기 - POST /products -테스트미작성, RequestBody Validation 추가예정
//TODO        고양이 장난감 수정하기 - PATCH /products/{id}   -테스트 미작성
//TODO        고양이 장난감 삭제하기 - DELETE /products/{id}  -테스트 미작성
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable Long id) {
        return new ProductDto(productService.getProduct(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return new ProductDto(productService.addProduct(productDto));
    }

    @PatchMapping("/{id}")
    public ProductDto patchProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return new ProductDto(productService.updateProduct(id, productDto));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteTask(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ProductNotFoundException.class)
    public String productNotFoundExceptionHandler(ProductNotFoundException exception) {
        return exception.getMessage();
    }

}
