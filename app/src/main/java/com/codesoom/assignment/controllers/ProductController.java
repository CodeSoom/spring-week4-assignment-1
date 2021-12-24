package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import lombok.RequiredArgsConstructor;
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

import java.util.List;

/**
 * 상품에 대한 HTTP 요청의 처리를 담당합니다.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    /**
     * 상품목록을 리턴한다.
     * @return 상품목록
     */
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    /**
     * 주어진 id의 상품을 리턴한다.
     * @param id 상품 id
     * @return 주어진 id의 상품
     */
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 상품을 저장하고 저장된 상품을 리턴한다.
     * @param source 저장할 상품 source
     * @return 저장된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product source) {
        return productService.saveProduct(source);
    }

    /**
     * 주어진 id의 상품을 수정하고 수정된 상품을 리턴한다.
     * @param id 수정할 상품 id
     * @param source 수정할 상품 source
     * @return 수정된 상품
     */
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product source) {
        return productService.updateProduct(id, source);
    }

    /**
     * 주어진 id의 상품을 삭제한다.
     * @param id 삭제할 상품 id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
