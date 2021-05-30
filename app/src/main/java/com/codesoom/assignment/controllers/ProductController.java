package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;
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

@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    /**
     * 주어진 Service 객체로 초기화하며 ProductController 객체를 생성합니다.
     *
     * @param productService 주어진 Service 객체
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 200 상태코드와 함께, 모든 상품의 목록을 응답합니다.
     *
     * @return 상품 목록입니다.
     */
    @GetMapping
    public List<Product> list() {
        return productService.getAllProducts();
    }

    /**
     * 200 상태코드와 함께, id에 해당하는 상품을 응답합니다.
     *
     * @param id 상품의 id
     * @return id에 해당하는 상품
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    /**
     * 201 상태코드와 함께, 추가된 상품을 응답합니다.
     *
     * @param product 추가할 상품정보
     * @return 갱신된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    /**
     * 성공시 200 상태코드와 함께, 갱신된 상품을 응답합니다.
     *
     * @param id 갱신할 상품 id
     * @param product 갱신할 상품정보
     * @return 갱신한 상품
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    /**
     * 성공시 204 상태코드를 응답합니다.
     *
     * @param id 삭제할 상품 id
     * @exception EmptyResultDataAccessException
     *      id에 해당하는 상품이 없음
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
