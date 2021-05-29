package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EmptyResultDataAccessException(Math.toIntExact(id))));
    }

    @Override
    public Product save(Product product) {
        product.setId(generateId());
        products.add(product);
        return product;
    }

    @Override
    public void deleteById(Long id) {
        Product product = products.stream()
                                .filter(p -> p.getId().equals(id))
                                .findFirst()
                                .orElseThrow(() -> new EmptyResultDataAccessException(Math.toIntExact(id)));
        products.remove(product);
    }
}
