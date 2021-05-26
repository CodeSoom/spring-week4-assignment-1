package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.core.application.ProductService;
import com.codesoom.assignment.core.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 고양이 장난감 물품에 대한 Request 처리하여 Response를 반환합니다.
 */
@RestController
@CrossOrigin
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 모든 고양이 장난감 목록을 반환합니다.
     * @return 고양이 장난감 목록
     */
    @GetMapping
    public List<Product> products() {
        return productService.fetchProducts();
    }

    /**
     * 새로운 고양이 장난감을 등록합니다.
     * @param product
     * @return
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    /**
     * 요청 ID에 해당하는 고양이 장난감을 반환한다.
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public Product product(@PathVariable Long id) {
        return productService.fetchProductById(id);
    }

}
