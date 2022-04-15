package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 상품에 대한 조회의 작업을 수행한다.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> foundProduct = productRepository.findById(id);
        if(foundProduct.isEmpty()) throw new ProductNotFoundException(id);
        return foundProduct.get();
    }
}
