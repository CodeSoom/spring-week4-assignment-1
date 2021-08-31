package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.exceptions.ProductNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * 상품 목록 관리를 담당합니다.
 */
@Service
@Transactional
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품 하나를 추가합니다.
     *
     * @param product 추가할 상품
     * @return 추가된 상품
     */
    public Product create(Product product) {
        return productRepository.save(product);
    }

    /**
     * 모든 상품을 리턴합니다.
     *
     * @return 모든 상품
     */
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * 식별자로 상품을 찾아 리턴합니다.
     *
     * @param id 식별자
     * @return 찾은 상품
     */
    public Product findById(long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 식별자로 상품을 찾은 후 수정합니다.
     *
     * @param id     식별자
     * @param source 바꿀 상품 정보
     */
    public Product update(long id, Product source) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        product.updateInfo(source);

        return productRepository.save(product);
    }

    /**
     * 식별자로 상품을 찾아 삭제합니다.
     * @param id 식별자
     */
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}
