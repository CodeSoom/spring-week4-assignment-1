package com.codesoom.assignment.application;

import javax.transaction.Transactional;

import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;

import org.springframework.stereotype.Service;

/**
 * Product리소스 비즈니스 로직 처리를 담당한다.
 */
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Product를 저장하고 리턴한다.
     * 
     * @param product id를 제외한 Product 데이터
     * @return id를 포함한 모든 Product 데이터
     */
    public Product createProduct(final Product product) {
        return productRepository.save(product);
    }

    /**
     * Product를 찾아 리턴한다.
     * 
     * @param id 찾을 Product의 id
     * @return 찾은 Product 개체
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    public Product detailProduct(final Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
