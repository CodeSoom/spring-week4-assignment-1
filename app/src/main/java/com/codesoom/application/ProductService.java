package com.codesoom.application;

import com.codesoom.domain.Product;
import com.codesoom.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {
    private final Map<Long, Product> products = new LinkedHashMap<>();
    private Long newId = 0L;

    public List<Product> getProducts() {
        return new ArrayList<Product>(products.values());
    }

    public Optional<Product> getProduct(Long id) {
        Optional<Product> product = findProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException("요청한 " + id + "의 Product가 없습니다.");
        }
        return product;
    }

    public Product createProduct(Product product) {
        Long id = generateId();
        product.setId(id);
        products.put(id, product);

        return product;
    }

    public List<Product> deleteProduct(Long id) {
        Optional<Product> product = findProduct(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundException("요청한 " + id + "의 Product가 없습니다.");
        }
        products.remove(id);
        return getProducts();
    }

    private Optional<Product> findProduct(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id가 null이므로 Task를 찾을 수 없습니다.");
        }
        return Optional.ofNullable(products.get(id));
    }

    private synchronized Long generateId() {
        newId += 1;
        return newId;
    }
}
