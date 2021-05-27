package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
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
 * 상품 관련 http 요청 처리를 담당
 */
@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품 목록 요청을 받음
     * @return 상품 목록
     */
    @GetMapping("")
    public List<Product> getProductList() {
        return productService.getProductList();
    }

    /**
     * 상품 상세정보 요청을 받음
     * @param productId 요청 한 상품의 Id
     * @return 요청 한 상품
     */
    @GetMapping("/{productId}")
    public Product getDetailProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    /**
     * 새 상품 등록 요청을 받음
     * @param newProduct 새로 등록할 상품 내용
     * @return 새로 등록한 상품
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product newProduct) {
        return productService.createProduct(newProduct);
    }

    /**
     * 상품 수정 요청을 받음
     * @param productId 수정 요청한 상품의 Id
     * @param product 상품의 수정 내용
     * @return 수정한 상품
     */
    @PatchMapping("/{productId}")
    public Product updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        return productService.updateProduct(productId, product);
    }

    /**
     * 상품 삭제 요청을 받음
     * @param productId 삭제 요청한 상품의 Id
     */
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
    }
}
