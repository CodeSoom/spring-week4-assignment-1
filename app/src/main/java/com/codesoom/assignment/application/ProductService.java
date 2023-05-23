package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 1. getProducts -> 목록
 * 2. getProduct -> 상세 정보
 * 3. createProduct -> 상품 추가
 * 4. updateProduct -> 상품 수정
 * 5. deleteProduct -> 상품 삭제
 */

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }


    public Product updateProduct(Long id, Product product) {
        return null;
    }

    public Product deleteProduct(Long id) {
        return null;
    }
}
