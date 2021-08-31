package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

/**
 * 상품 목록 관리를 담당합니다.
 */
@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 하나를 추가합니다.
     * @param product 추가할 상품
     * @return 추가된 상품
     */
    public Product create(Product product) {
        return productRepository.save(product);
    }

    /**
     * 모든 상품을 리턴합니다.
     * @return 모든 상품
     */
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }
}
