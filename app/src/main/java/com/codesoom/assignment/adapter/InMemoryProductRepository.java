package com.codesoom.assignment.adapter;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductId;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final HashMap<Long, Product> inMemoryMap = new HashMap<>();
    private Long lastId = 1L;

    @Override
    public void save(Product product) {
        inMemoryMap.put(product.productId().id(), product);
    }

    @Override
    public void remove(Product product) {
        inMemoryMap.remove(product.productId().id());
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
    public synchronized ProductId nextId() {
        return new ProductId(lastId++);
    }
}
