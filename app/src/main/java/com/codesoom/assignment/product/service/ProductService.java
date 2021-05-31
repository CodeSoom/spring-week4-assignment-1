package com.codesoom.assignment.product.service;

import com.codesoom.assignment.error.exception.ProductNotFoundException;
import com.codesoom.assignment.product.domain.Product;
import com.codesoom.assignment.product.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(Product product) {
        return productRepository.save(product);
    }

    public Product get(Long id) {
        return productRepository.findById(id)
                                .orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> list() {
        return productRepository.findAll();
    }

    public Product update(Long id, Product productData) {
        Product product = get(id);

        product.change(
                productData.getName(),
                productData.getMaker(),
                productData.getPrice(),
                productData.getImageUrl()
                      );

        return product;
    }

    public void delete(Long id) {
        // if (!productRepository.existsById(id)) {
        //     throw new ProductNotFoundException(); // FIXME: jacoco does
        //      not cover it
        // }
        // productRepository.deleteById(id);
        Product product = get(id);
        productRepository.delete(product);
    }
}
