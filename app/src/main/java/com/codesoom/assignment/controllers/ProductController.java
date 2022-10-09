package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.entity.Product;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 상품리스트를 리턴한다.
     * @return productList
     */
    @GetMapping
    public List<Product> getProductList() {
        return productService.getList();
    }

    /**
     * 상품을 찾아 리턴한다.
     *
     * @param id
     * @return id로 찾은 상품
     */
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id);
    }

    /**
     *
     * 저장된 상품을 리턴한다.
     *
     * @param source
     * @return 저장된 상품
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product source) {
        return productService.create(source);
    }

    /**
     * 상품의 정보를 업데이트 한다.
     *
     * @param id
     * @param updateSource
     * @return 업데이트한 상품
     */
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updateSource) {
        return productService.update(id, updateSource);
    }

    /**
     * 상품을 삭제 후 리턴한다.
     *
     * @param id
     * @return 삭제한 상품
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Product deleteProduct(@PathVariable Long id) {
        return productService.remove(id);
    }

}
