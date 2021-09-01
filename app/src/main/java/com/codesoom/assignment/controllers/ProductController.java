package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * 상품 관련 HTTP 요청 처리를 담당합니다.
 */
@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 상품을 추가하고, 리턴합니다.
     *
     * @param product 추가할 상품
     * @return 추가된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    /**
     * 전체 상품 목록을 리턴합니다.
     *
     * @return 전체 상품
     */
    @GetMapping
    public Iterable<Product> getAll() {
        return productService.getAllProducts();
    }

    /**
     * 식별자로 상품을 찾고, 리턴합니다.
     *
     * @param id 식별자
     * @return 찾은 상품
     */
    @GetMapping("{id}")
    public Product get(@PathVariable long id) {
        return productService.getProduct(id);
    }

    /**
     * 식별자로 상품을 찾아 수정하고, 리턴합니다.
     *
     * @param id     식별자
     * @param source 수정할 상품 정보
     * @return 수정된 상품
     */
    @PatchMapping("{id}")
    public Product update(@PathVariable long id, @RequestBody Product source) {
        return productService.updateProduct(id, source);
    }

    /**
     * 식별자로 상품을 찾고, 삭제합니다.
     *
     * @param id 식별자
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        productService.deleteProduct(id);
    }

    /**
     * 상품을 찾지 못할 경우를 처리합니다.
     *
     * @return HTTP 응답 코드
     */
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Product> handleProductNotFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

