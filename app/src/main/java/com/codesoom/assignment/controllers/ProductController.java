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
     * ProductList를 리턴한다
     * @return productList
     */
    @GetMapping
    public List<Product> getProductList() {
        return productService.getList();
    }

    /**
     * id로 product 객체를 찾는다
     *
     * @param id
     * @return id로 찾은 product 객체
     */
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id);
    }

    /**
     * product 객체를 생성,db에 저장한다
     *
     * @param source
     * @return 저장된 product 객체를 리턴한다
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product source) {
        return productService.create(source);
    }

    /**
     * id로 찾은 product 객체의 정보를 업데이트 한다
     *
     * @param id
     * @param updateSource
     * @return 업데이트한 product 를 리턴한다
     */
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updateSource) {
        return productService.update(id, updateSource);
    }

    /**
     * 해당 id를 가진 product 를 삭제한다
     *
     * @param id
     * @return 삭제한 Product 를 리턴한다
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Product deleteProduct(@PathVariable Long id) {
        return productService.remove(id);
    }

}
