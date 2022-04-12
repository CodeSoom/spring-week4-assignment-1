package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품 조회에 대한 작업을 관리합니다.
 */
@Service
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 전체 목록을 리턴합니다.
     */
    public List<Product> getProducts() {
        Iterable<Product> source = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        source.forEach(products::add);
        return products;
    }

    public Product getProduct(Long productId) {
        return null;
    }
}
