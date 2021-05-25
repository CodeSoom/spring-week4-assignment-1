package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.error.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }


    public Product update(Long id, Product product) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        findProduct.update(product);

        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public void delete(Long id) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.delete(findProduct);
    }
}
