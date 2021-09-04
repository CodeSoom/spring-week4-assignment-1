package com.codesoom.assignment.application;

import javax.transaction.Transactional;

import java.util.List;

import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;

import org.springframework.stereotype.Service;

/**
 * Product 리소스관련 수행할 작업을 정의하고, 도메인 개체에 수행을 위임한다.
 *
 * @see ProductRepository Product 리소스 생성, 수정, 삭제, 조회 작업을 위임받는다.
 */
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Product 생성을 도메인 개체에 위임힌다.
     *
     * @param product Product 생성에 필요한 데이터
     * @return 생성한 Product
     */
    public Product createProduct(final Product product) {
        return productRepository.save(product);
    }

    /**
     * Product 목록 조회를 도메인 개체에 위힘한다.
     *
     * @return Product 목록
     */
    public List<Product> listProduct() {
        return productRepository.findAll();
    }

    /**
     * Product 조회를 도메인 개체에 위임힌다.
     *
     * @param id 찾을 Product의 id
     * @return 찾은 Product
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    public Product detailProduct(final Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Product 수정을 도메인 개체에 위임힌다.
     *
     * @param id 수정할 Product의 id
     * @param product 수정할 Product 데이터
     * @return 수정한 Product
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    public Product updateProduct(final Long id, final Product source) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id)).update(source);
    }

    /**
     * Product를 삭제를 도메인 개체에 위임힌다.
     *
     * @param id 삭제할 Product의 id
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    public void deleteProduct(final Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
