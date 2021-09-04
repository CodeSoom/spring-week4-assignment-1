package com.codesoom.assignment.controller;

import java.util.List;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.CreateProductDto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
     * 모든 Product의 목록을 리턴한다.
     *
     * @return 모든 Product 목록
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> list() {
        return productService.listProduct();
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
        Product product = new Product(createProductDto.getTitle());
        return productService.createProduct(product);
    }

    /**
     * Product를 찾아 리턴한다.
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

    /**
     * Product를 업데이트하고 리턴한다.
     *
     * @param id 찾을 Product의 id
     * @param createProductDto id를 제외한 Product 데이터
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    @RequestMapping(
        value = "{id}", method = { RequestMethod.PUT, RequestMethod.PATCH }
    )
    @ResponseStatus(HttpStatus.OK)
    public Product update(
        @PathVariable final Long id, @RequestBody final CreateProductDto createProductDto
    ) {
        Product source = new Product(createProductDto.getTitle());
        return productService.updateProduct(id, source);
    }

    /**
     * Product를 삭제한다.
     *
     * @param id 삭제할 Product의 id
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable final Long id) {
        productService.deleteProduct(id);
    }
}