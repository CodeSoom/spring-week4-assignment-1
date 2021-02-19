package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 상품에 대한 HTTP 요청 핸들러.
 *
 * @see ProductService
 * @see Product
 */
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 주어진 product을 저장한 뒤, product를 응답힙니다.
     *
     * @param source 저장하고자 하는 product
     * @return 저장된 product
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product source) {
        return productService.create(source);
    }

    /**
     * 모든 product 리스트를 응답합니다.
     */
    @GetMapping
    public List<Product> list() {
        return productService.findAll();
    }

    /**
     * 주어진 id에 해당하는 product를 찾아 응답합니다.
     *
     * @param id 찾고자 하는 product의 식별자
     * @return id와 일치하는 product
     */
    @GetMapping(path = "{id}")
    public Product find(@PathVariable Long id) {
        return productService.find(id);
    }

    /**
     * 주어진 id에 해당하는 product를 수정한 후 수정된 product를 응답합니다.
     *
     * @param id     수정하고자 하는 product의 식별자
     * @param source 수정하고자 하는 product
     * @return 수정된 product
     */
    @PatchMapping(path = "{id}")
    public Product update(@PathVariable Long id, @RequestBody Product source) {
        return productService.update(id, source);
    }

    /**
     * 주어진 id에 해당하는 product를 삭제합니다.
     *
     * @param id 삭제하고자 하는 product의 식별자
     */
    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
