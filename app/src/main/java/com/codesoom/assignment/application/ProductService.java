package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
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
        return (List<Product>) productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public Product saveProduct(Product source) {
        Product.insert(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImage()
        );

        return productRepository.save(source);
    }

    public Product updateProduct(Long id, Product source) {
        Product product = productRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        product.update(
                source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImage()
        );

        return product;
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        productRepository.delete(product);
    }
}
