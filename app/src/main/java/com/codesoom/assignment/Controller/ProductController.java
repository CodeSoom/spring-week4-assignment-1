package com.codesoom.assignment.controller;

import java.util.ArrayList;
import java.util.List;

import com.codesoom.assignment.Dto.CreateProductDto;
import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Product 리소스 Http 요청을 담당한다.
 */
@RestController
@CrossOrigin
@RequestMapping("/products")
public final class ProductController {
    private final ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    /**
     * 저장되어 있는 모든 Product의 목록을 리턴한다.
     * 
     * @return 모든 Product 목록
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> list() {
        return new ArrayList<>();
    }

    /**
     * Product를 생성하고 리턴한다.
     * 
     * @param createProductDto id를 제외한 Product 데이터
     * @return id를 포함한 Product 데이터
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody final CreateProductDto createProductDto) {
        Product product = new Product(null, createProductDto.getTitle());
        return productService.createProduct(product);
    }

    /**
     * 특정 Product를 찾아 리턴한다.
     * 
     * @param id 찾을 Product의 id
     * @return 찾은 Product
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product detail(@PathVariable final Long id) {
        return productService.detailProduct(id);
    }
}
