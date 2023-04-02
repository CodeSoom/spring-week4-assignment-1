package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
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
    public List<Product> getProducts() {
        //TODO 실제로 구현할 것
        return List.of(new Product("쥐돌이", "냥이월드", 5000));
    }

    public Product getProduct(Long id) {
        //TODO 실제로 구현할 것
        return null;
    }

    public Product createProduct(Product product) {
        return null;
    }


    public Product updateProduct(Long id, Product product) {
        return null;
    }

    public Product deleteProduct(Long id) {
        return null;
    }
}
