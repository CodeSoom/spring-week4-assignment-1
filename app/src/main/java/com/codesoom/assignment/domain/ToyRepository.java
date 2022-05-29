package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ToyRepository implements ProductRepository {
    private static final Map<Long, Product> products = new HashMap<>();
    private Long newId = 0L;

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public Product save(Product product) {
        product.setId(generateId());
        products.put(product.getId(), product);

        return product;
    }

    @Override
    public Product update(Long id, Product newProduct) {
        newProduct.setId(id);
        products.put(newProduct.getId(), newProduct);

        return newProduct;
    }

    @Override
    public void delete(Long id) {
        products.remove(id);
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
