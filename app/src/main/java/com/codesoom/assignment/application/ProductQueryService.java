package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 상품 조회 담당
 */
@Service
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 전체 목록 반환합니다.
     */
    public List<Product> getProducts() {
        Iterable<Product> source = productRepository.findAll();
        List<Product> products = new ArrayList<>();
        source.forEach(products::add);
        return products;
    }

    /**
     * 단일 상품 반환합니다.
     *
     * @param productId 상품 아이디
     * @throws ProductNotFoundException 상품을 찾을 수 없는 경우
     */
    public Product getProduct(final Long productId) {

        return productRepository.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}
