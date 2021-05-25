package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.error.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 상품과 관련된 비즈니스 로직을 담당합니다.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * 모든 상품을 리턴합니다.
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * 주어진 id에 해당하는 상품을 리턴합니다.
     *
     * @param id 상품의 식별자
     * @return 주어진 id에 해당하는 상품
     * @throws ProductNotFoundException 주어진 id가 상품 목록에 없는 경우
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    /**
     * 새롭게 주어진 상품에 대한 정보를 저장하고, 그 상품을 리턴합니다.
     *
     * @param product 새롭게 만든 상품
     * @return 저장된 상품
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾은 후 수정합니다.
     *
     * @param id 상품의 식별자
     * @param product 수정하려는 상품
     * @return 수정된 상품
     * @throws ProductNotFoundException 주어진 id가 상품 목록에 없는 경우
     */
    public Product update(Long id, Product product) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        findProduct.update(product);

        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    /**
     * 주어진 id에 해당하는 상품을 찾아 삭제합니다.
     *
     * @param id 상품의 식별자
     * @throws ProductNotFoundException 주어진 id가 상품 목록에 없는 경우
     */
    public void delete(Long id) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        productRepository.delete(findProduct);
    }
}
