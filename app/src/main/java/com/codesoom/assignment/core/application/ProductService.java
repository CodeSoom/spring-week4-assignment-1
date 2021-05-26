package com.codesoom.assignment.core.application;

import com.codesoom.assignment.core.domain.Product;
import com.codesoom.assignment.core.domain.ProductRepository;
import com.codesoom.assignment.core.infra.JpaToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 고양이 장난감 데이터를 가공하여 반환하거나 처리합니다.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(JpaToyRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 고양이 장난감 목록을 반환합니다.
     * @return
     */
    public List<Product> toys() {
        return productRepository.findAll();
    }

}
