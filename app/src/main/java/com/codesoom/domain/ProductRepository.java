package com.codesoom.domain;

import com.codesoom.exception.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new LinkedHashMap<>();
    private Long newId = 0L;

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> find(Long id) {
        Optional<Product> product = findProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException("요청한 " + id + "의 Product를 찾지 못했습니다..");
        }
        return product;
    }

    public Product save(Product product) {
        Long id = generateId();
        product.setId(id);
        products.put(id, product);

        return product;
    }

    public void remove(Optional<Product> product) {
        products.remove(product.get().getId());
    }

    private Optional<Product> findProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id가 null이므로 Product를 찾을 수 없습니다.");
        }
        return Optional.ofNullable(products.get(id));
    }

    private synchronized Long generateId() {
        newId += 1;
        return newId;
    }
}
