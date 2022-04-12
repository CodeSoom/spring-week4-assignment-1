package com.codesoom.assignment.infra;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private Long newId = 0L;

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return Optional.of(product);
            }
        }
        throw new ProductNotFoundException(id);
    }

    @Override
    public Product save(Product product) {
        product.setId(generateId());
        products.add(product);
        return product;
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
    }

    @Override
    public void deleteAll() {
        products.clear();
    }


    private Long generateId() {
        newId += 1L;
        return newId;
    }
}