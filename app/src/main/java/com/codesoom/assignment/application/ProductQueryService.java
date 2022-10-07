package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 상품 조회를 담당합니다.
 */
@Service
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품을 조회합니다.
     * @param id 상품을 찾을 아이디
     * @return 찾은 상품
     * @throws ProductNotFoundException 해당하는 아이디의 상품을 찾지 못한 경우
     */
    public Product get(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 상품을 찾지 못했습니다."));
    }

    /**
     * 모든 상품을 조회합니다.
     * @return 저장된 모든 상품
     */
    public List<Product> getAll() {
        return productRepository.findAll();
    }
}
