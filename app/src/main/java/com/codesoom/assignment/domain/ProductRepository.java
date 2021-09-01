package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private HashMap<Long, Product> products;
    private Long nextId;

    private final Object nextIdLock = new Object();

    public ProductRepository() {
        this.products = new HashMap<Long, Product>();
        this.nextId = 1L;
    }

    public Product save(Product product) {
        Long newId;
        synchronized (nextIdLock) {
            newId = generateId();
        }

        product.setId(newId);
        this.products.put(newId, product);

        return product;
    }

    public List<Product> findAll() {
        return new ArrayList<Product>(this.products.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(this.products.get(id));
    }

    public void update(Long id, Product product) {
        Product updatedProduct = findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        updatedProduct.setName(product.getName());
        updatedProduct.setMaker(product.getMaker());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setImageURI(product.getImageURI());
    }

    public void deleteById(Long id) {
        if(!existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        this.products.remove(id);
    }

    private Long generateId() {
        return nextId++;
    }

    private boolean existsById(Long id) {
        return this.products.containsKey(id);
    }
}
