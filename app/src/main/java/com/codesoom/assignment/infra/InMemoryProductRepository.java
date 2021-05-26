package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final List<Product> products = new ArrayList<>();
    private Long currentProductId = 0L;

    private Long generateId() {
        currentProductId += 1;
        return currentProductId;
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public Product find(Long id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product save(Product product) {
        product.setId(generateId());
        products.add(product);
        return product;
    }

    @Override
    public Product update(Long id, Product product) {
        Product targetProduct = find(id);
        targetProduct.updateBy(product);
        return targetProduct;
    }

    @Override
    public Product remove(Long id) {
        Product product = find(id);
        products.remove(product);
        return product;
    }
}
