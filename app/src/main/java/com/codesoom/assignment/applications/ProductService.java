package com.codesoom.assignment.applications;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domains.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private List<Product> Products = new ArrayList<>();
    private Long newId = 0L;

    public List<Product> getProducts() {
        return Products;
    }

    public Product getProduct(Long id) {
        return Products.stream()
                .filter(Product -> Product.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product source) {
        source.setId(generateId());
        Products.add(source);
        return source;
    }

    public Product updateProduct(Long id, Product source) {
        Product Product = getProduct(id);
        Product.setTitle(source.getTitle());

        return Product;
    }

    public Product deleteProduct(Long id) {
        Product Product = getProduct(id);
        Products.remove(Product);

        return Product;
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
