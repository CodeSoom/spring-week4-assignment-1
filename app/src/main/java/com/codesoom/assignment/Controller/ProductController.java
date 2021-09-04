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
 * Product 리소스관련 Http 요청을 처리하고, 서비스 개체가 정의한 작업을 수행하게 한다.
 *
 * @see ProductService Product 리소스 생성, 수정, 삭제, 조회를 수행한다.
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
     * 서비스 개체가 Product 생성을 수행하게 한다.
     *
     * @param createProductDto Product 생성에 필요한 데이터
     * @return 생성한 Product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody final CreateProductDto createProductDto) {
        Product product = new Product(createProductDto.getTitle());
        return productService.createProduct(product);
    }

    /**
     * 서비스 개체가 Product 목록 조회를 수행하게 한다.
     *
     * @return Product 목록
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> list() {
        return productService.listProduct();
    }

    /**
     * 서비스 개체가 Product 조회를 수행하게 한다.
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
     * 서비스 개체가 Product 수정을 수행하게 한다.
     *
     * @param id 수정할 Product의 id
     * @param createProductDto 수정할 Product 데이터
     * @return 수정한 Product
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    @RequestMapping(
        value = "{id}", method = { RequestMethod.PUT, RequestMethod.PATCH }
    )
    @ResponseStatus(HttpStatus.OK)
    public Product update(
        @PathVariable final Long id, @RequestBody final CreateProductDto createProductDto
    ) {
        Product product = new Product(createProductDto.getTitle());
        return productService.updateProduct(id, product);
    }

    /**
     * 서비스 개체가 Product 삭제를 수행하게 한다.
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
