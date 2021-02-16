package com.codesoom.assignment.adapter;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductId;
import com.codesoom.assignment.domain.ProductRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class InMemoryProductRepository implements ProductRepository {
    private final HashMap<Long, Product> inMemoryMap = new HashMap<>();
    private Long lastId = 1L;

    @Override
    public void save(Product product) {
        inMemoryMap.put(product.productId().id(), product);
    }

    @Override
    public Optional<Product> find(Long id) {
        Product product = inMemoryMap.get(id);
        if (product == null) {
            return Optional.empty();
        }
        return Optional.of(product);
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(inMemoryMap.values());
    }

    @Override
    public ProductId nextId() {
        return new ProductId(lastId++);
    }
}
