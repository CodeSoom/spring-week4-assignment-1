package com.codesoom.assignment.product.controller;

import com.codesoom.assignment.product.application.ProductService;
import com.codesoom.assignment.product.domain.Product;
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
 * 상품에 대한 Http Request 요청을 처리합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@CrossOrigin
public class ProductController {

    private final ProductService productService;


    /**
     * 상품 전체 목록을 반환합니다.
     *
     * @return 상품 목록
     */
    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    /**
     * 식별자를 이용해 상품 상세정보를 조회한다.
     *
     * @param id 상품 식별자
     * @return 상품 상세 정보
     */
    @GetMapping("/{id}")
    public Product findById(@PathVariable long id) {
        return productService.findById(id);
    }

    /**
     * 새로운 상품 정보를 생성한다.
     *
     * @param product 상품 정보
     * @return 생성된 상품 상세 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.save(product);
    }

    /**
     * 식별자를 이용해 조회한 상품 정보를 수정한다.
     *
     * @param id     상품 식별자
     * @param product 수정할 상품 정보
     * @return 수정된 상품 상세 정보
     */
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    /**
     * 식별자를 이용해 조회한 상품 정보를 삭제한다.
     *
     * @param id 삭제할 상품 식별자
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable long id) {
        final Product foundProduct = productService.findById(id);

        productService.deleteProduct(foundProduct);
    }


}
