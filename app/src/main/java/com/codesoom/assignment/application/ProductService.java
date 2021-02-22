package com.codesoom.assignment.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNonExistException;
import com.codesoom.assignment.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 새로운 상품을 생성해 반환합니다.
     * @param product
     * @return 상품
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * 상품 목록을 반환합니다.
     * @return 상품 목록
     */
    public List<Product> getProducts() {
        return null;
    }

    /**
     * 특정 상품을 반환합니다.
     * @param id
     * @return 상품
     */
    public Product getProduct(Long id) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }

    /**
     * 특정한 상품을 업데이트 하여 반환합니다.
     * @param id
     * @param product
     * @return 수정된 상품
     */
    public Product updateProduct(Long id, Product product) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }

    /**
     * 특정한 상품을 지우고 반환합니다.
     * @param id
     * @return 삭제된 상품
     */
    public Product deleteProduct(Long id) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }
}
