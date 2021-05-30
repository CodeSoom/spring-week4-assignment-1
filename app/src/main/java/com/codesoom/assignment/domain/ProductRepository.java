package com.codesoom.assignment.domain;

import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    /**
     * 상품 목록을 반환합니다.
     *
     * @return 상품 목록
     */
    List<Product> findAll();

    /**
     * id에 해당하는 상품을 반환합니다.
     *
     * @param id 주어진 상품 id
     * @return id에 해당하는 상품
     */
    Optional<Product> findById(Long id);

    /**
     * 상품을 추가합니다.
     *
     * @param product 추가할 상품
     * @return 추가한 상품
     */
    Product save(Product product);

    /**
     * id에 해당하는 상품을 제거합니다.
     *
     * @param id 주어진 id
     */
    void deleteById(Long id);
}
