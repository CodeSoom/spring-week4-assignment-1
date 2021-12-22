// TODO: Cotroller List 구현하기 (테스트 코드 통과)
// TODO: Cotroller Detail 구현하기
// TODO: Cotroller Create 구현하기
// TODO: Cotroller update 구현하기
// TODO: Cotroller patch 구현하기
// TODO: Cotroller delete 구현하기

package com.codesoom.assignment.controller;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
    Product product = new Product();

    private List<Product> products = new ArrayList<>();
    private Long newId = 0L;

    public ProductController(Product product) {
        this.product = product;
    }

    @GetMapping
    public List<Product> list() {
        return products;
    }

    @GetMapping("{id}")
    public Product detail(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PostMapping
    public Product create(Product source) {
        Product product = new Product();
        product.setId(generateId());
        product.setTitle(source.getTitle());

        products.add(product);

        return product;

    }



    private Long generateId() {
        newId += 1;

        return newId;
    }
}

