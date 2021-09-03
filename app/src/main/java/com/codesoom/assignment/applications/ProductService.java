package com.codesoom.assignment.applications;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.domains.ProductRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private List<Product> Products = new ArrayList<>();
    private Long newId = 0L;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product source) {
        productRepository.save(source);
        return source;
    }

    public Product updateProduct(Long id, Product source) {
        Product product = getProduct(id);
        product.setName(source.getName());
        product.setPrice(source.getPrice());
        product.setImage(source.getImage());
        return product;
    }

    public Product deleteProduct(Long id) {
        Product product = getProduct(id);
        productRepository.delete(product);
        return product;
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }
}
