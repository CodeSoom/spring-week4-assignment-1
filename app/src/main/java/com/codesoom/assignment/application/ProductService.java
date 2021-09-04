package com.codesoom.assignment.application;

import javax.transaction.Transactional;

import java.util.List;

import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;

import org.springframework.stereotype.Service;

/**
 * Product 리소스 작업(CRUD)을 정의하고, ProductRepository개체에 수행을 위임한다.
 *
 * @see ProductRepository
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

    /**
     * Product 목록을 리턴한다.
     *
     * @return 저장된 Product 목록
     */
    public List<Product> listProduct() {
        return productRepository.findAll();
    }

    /**
     * Product 업데이트하고 리턴한다.
     *
     * @param id 업데이트할 Product의 id
     * @param product id를 제외한 Product 데이터
     * @return 업데이트한 Product 데이터
     * @throws ProductNotFoundException Product를 찾지 못한 경우
     */
    public Product updateProduct(final Long id, final Product source) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id)).update(source);
    }

    /**
     * Product를 삭제한다.
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
