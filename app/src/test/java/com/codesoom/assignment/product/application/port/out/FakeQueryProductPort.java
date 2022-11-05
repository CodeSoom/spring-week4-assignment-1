package com.codesoom.assignment.product.application.port.out;

import com.codesoom.assignment.product.domain.Product;

import java.util.List;
import java.util.Optional;

public class FakeQueryProductPort implements QueryProductPort {
    private final List<Product> products;

    public FakeQueryProductPort(List<Product> products) {
        this.products = products;
    }

    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }
}
