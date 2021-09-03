package com.codesoom.assignment.controllers;
import com.codesoom.assignment.applications.ProductService;
import com.codesoom.assignment.domains.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    @Autowired
    ProductService ProductService;

    public ProductController(ProductService ProductService) {
        this.ProductService = ProductService;
    }

    @GetMapping
    public List<Product> list() {
        return ProductService.getProducts();
    }

    @GetMapping("{id}")
    public Product detail(@PathVariable Long id) {
        return ProductService.getProduct(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product Product) {
        return ProductService.createProduct(Product);
    }

    @PatchMapping("{id}")
    public Product patch(@PathVariable Long id, @RequestBody Product Product) {
        return ProductService.updateProduct(id, Product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ProductService.deleteProduct(id);
    }
}
