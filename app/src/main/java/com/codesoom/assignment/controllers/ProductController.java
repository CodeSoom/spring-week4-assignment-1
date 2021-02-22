package com.codesoom.assignment.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 서비스에 상품 생성을 요청합니다.
     * @param product
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    /**
     * 서비스에 상품 목록을 요청합니다.
     * @return 상품 목록
     */
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    /**
     * 서비스에 특정한 상품을 요청합니다.
     * @return 검색된 상품
     */
    @GetMapping("{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 서비스에 특정한 상품 수정을 요청합니다.
     * @return 수정된 상품
     */
    @PutMapping("{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    /**
     * 서비스에 특정한 상품 삭제를 요청합니다.
     * @return 삭제된 상품
     */
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Product deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }
}
