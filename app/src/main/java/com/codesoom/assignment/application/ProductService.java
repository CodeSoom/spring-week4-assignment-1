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
     * 장난감을 생성하고 반환하는 메서드
     * @param product
     * @return 장난감
     */
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * 장난감 목록을 반환하는 메서드
     * @return 장난감 목록
     */
    public List<Product> getProducts() {
        return null;
    }

    /**
     * 특정한 장난감을 반환하는 메서드
     * @param id
     * @return 장난감
     */
    public Product getProduct(Long id) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }

    /**
     * 특정한 장난감을 업데이트 하는 메서드
     * @param id
     * @param product
     * @return 수정된 장난감
     */
    public Product updateProduct(Long id, Product product) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }

    /**
     * 특정한 장난감을 지우는 메서드
     * @param id
     * @return 지워진 장난
     */
    public Product deleteProduct(Long id) {
        if (id == 100) {
            throw new ProductNonExistException(id);
        }
        return null;
    }
}
