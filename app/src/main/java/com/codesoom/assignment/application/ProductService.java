package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductService {

    /**
     * 장난감을 조회합니다.
     * @param id 장난감을 찾을 아이디
     * @return 찾은 장난감
     */
    Product get(Long id);

    /**
     * 모든 장난감을 조회합니다.
     * @return 저장된 모든 장난감
     */
    List<Product> getAll();

    /**
     * 장난감을 생성하여 반환합니다.
     * @param product 생성할 장난감
     * @return 생성된 장난감
     */
    Product create(Product product);

    /**
     * 장난감을 수정하여 반환합니다.
     * @Param id 수정할 장난감 아이디
     * @param product 수정할 장난감
     * @return 수정된 장난감
     */
    Product update(Long id, Product product);

    /**
     * 장난감을 삭제합니다.
     * @param id 삭제할 장난감 아이디
     */
    void deleteById(Long id);
}
