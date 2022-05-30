package com.codesoom.assignment.repository;

import com.codesoom.assignment.domain.Product;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 테스트 시에만 사용하기 위한 in memory repository
 */
@Primary
@Repository
public class InMemoryProductRepository implements ProductRepository{

    private Long maxId = 0L;
    private Map<Long, Product> products = new HashMap<>();

    private Long generateId() {
        this.maxId += 1;
        return this.maxId;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Optional<Product> findById(Long id) {

        return products.values().stream()
                .filter(product -> product.checkMyId(id))
                .findFirst();
    }

    @Override
    public Product save(Product product) {
        if (product.isRegistered()) {
            products.put(product.id(), product);
            return product;
        }

        Product createdProduct = Product.creatNewProduct(generateId(), product);
        products.put(createdProduct.id(), createdProduct);
        return createdProduct;
    }

    @Override
    public void delete(Product product) {
        products.remove(product.id());
    }
}
