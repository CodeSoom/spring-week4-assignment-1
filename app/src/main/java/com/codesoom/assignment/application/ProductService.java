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

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 상품을 추가하고, 리턴합니다.
     *
     * @param product 추가할 상품
     * @return 추가된 상품
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * 모든 상품을 리턴합니다.
     *
     * @return 모든 상품
     */
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * 상품을 찾아 리턴합니다.
     *
     * @param id 식별자
     * @return 찾은 상품
     * @throws ProductNotFoundException 식별자로 상품을 찾지 못한 경우
     */
    public Product getProduct(long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 상품을 찾아 수정하고, 리턴합니다.
     *
     * @param id     식별자
     * @param source 바꿀 상품 정보
     * @return 수정된 상품
     * @throws ProductNotFoundException 식별자로 상품을 찾지 못한 경우
     */
    public Product updateProduct(long id, Product source) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        product.updateInfo(source);

        return productRepository.save(product);
    }

    /**
     * 상품을 찾아 삭제합니다.
     *
     * @param id 식별자
     * @throws ProductNotFoundException 식별자로 상품을 찾지 못한 경우
     */
    public void deleteProduct(long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.delete(product);
    }
}
