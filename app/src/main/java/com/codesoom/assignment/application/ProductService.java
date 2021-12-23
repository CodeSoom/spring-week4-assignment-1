package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.errors.ProductNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

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

    public Product saveProduct(Product source) {
        Product.createSaveProduct(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return productRepository.save(source);
    }

    public Product updateProduct(Long id, Product source) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        product.update(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return product;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}
