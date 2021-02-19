package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFountException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 상품에 대한 비즈니스 로직을 처리합니다.
 *
 * @see Product
 */
@Service
@Transactional
public class ProductService {
    final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 새로운 product를 생성하여 반환합니다.
     *
     * @param source 새로운 product 정보
     * @return 생성된 새로운 product
     */
    public Product create(Product source) {
        return productRepository.save(source);
    }

    /**
     * 주어진 id와 일치하는 product를 찾아 반환합니다.
     *
     * @param id 찾고자 하는 product의 식별자
     * @return id와 일치하는 product
     * @throws ProductNotFountException 존재하지 않는 id가 주어진 경우
     */
    public Product find(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFountException(id));
    }

    /**
     * 모든 product 리스트를 반환합니다.
     *
     * @return product 리스트
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * 주어진 id와 일치하는 product를 수정하여 반한합니다.
     *
     * @param id     수정하고자 하는 product의 식별자
     * @param source 수정하고자 하는 product
     * @return 수정된 새로운 product
     * @throws ProductNotFountException 존재하지 않는 id가 주어진 경우
     */
    public Product update(Long id, Product source) {
        Product product = find(id);
        product.update(source);
        return create(product);
    }

    /**
     * 주어진 id와 일치하는 product를 삭제합니다.
     *
     * @param id 삭제하고자 하는 product의 식별자
     * @throws ProductNotFountException 존재하지 않는 id가 주어진 경우
     */
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFountException(id);
        }
        productRepository.deleteById(id);
    }

    /**
     * 모든 product를 삭제합니다.
     */
    public void clearData() {
        productRepository.deleteAll();
    }
}
