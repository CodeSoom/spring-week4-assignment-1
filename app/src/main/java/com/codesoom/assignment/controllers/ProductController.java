package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.entity.Product;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Product controller.
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    /**
     * Instantiates a new Product controller.
     *
     * @param productService the product service
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Gets product list.
     *
     * @return the product list
     */
    @GetMapping
    public List<Product> getProductList() {
        return productService.getList();
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by id
     */
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id);
    }

    /**
     * Create product product.
     *
     * @param source the source
     * @return the product
     */
    @PostMapping
    public Product createProduct(@RequestBody Product source) {
        return productService.create(source);
    }

    /**
     * Update product product.
     *
     * @param id           the id
     * @param updateSource the update source
     * @return the product
     */
    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product updateSource) {
        return productService.update(id, updateSource);
    }

    /**
     * Delete product product.
     *
     * @param id the id
     * @return the product
     */
    @DeleteMapping("/{id}")
    public Product deleteProduct(@PathVariable Long id) {
        return productService.remove(id);
    }

}
