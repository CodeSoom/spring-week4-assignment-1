package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface ProductManagable {

    /**
     * 모든 상품의 목록을 반환합니다.
     *
     * @return 상품 목록입니다.
     */
    List<Product> getAllProducts();

    /**
     * 지정된 id에 해당하는 상품을 반환합니다.
     *
     * @param id 상품의 id
     * @return id에 해당하는 상품
     */
    Product getProduct(Long id);

    /**
     * 주어진 상품을 저장합니다.
     *
     * @param product 주어진 상품
     * @return 저장된 상품
     */
    Product addProduct(Product product);

    /**
     * id에 해당하는 상품을 갱신합니다.
     *
     * @param id 상품의 id
     * @param product 갱신할 내용
     * @return 갱신된 상품
     */
    Product updateProduct(Long id, Product product);

    /**
     * id에 해당하는 상품을 삭제합니다.
     *
     * @param id 주어진 id
     */
    void deleteProduct(Long id);
}
