package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// TODO: Entity 오브젝트를 직접 쓰는 게 아닌 DTO 를 사용하는 것을 고려해보기

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public List<Product> list() {
        return service.getProducts();
    }

    public Product detail(Long id) {
        return service.getProduct(id);
    }

    public Product create(Product newProduct) {
        return service.createProduct(newProduct);
    }

    public Product update(long id, ProductDto productDto) {
        return service.updateProduct(id, productDto);
    }

    public void remove(Product product) {
        service.removeProduct(product);
    }
}
