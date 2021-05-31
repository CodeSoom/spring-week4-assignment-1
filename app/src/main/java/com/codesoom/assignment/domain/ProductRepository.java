package com.codesoom.assignment.domain;

import java.util.List;
import java.util.Optional;
//ToDo : ProductRepository 구현
// 고양이 장난감 전체 조회 -> findAll
// 고양이 장난감 상세 조회 -> findById
// 고양이 장난감 등록하기 -> save
// 고양이 장난감 수정하기 - findById -> save
// 고양이 장난감 삭제하기 -> delete

public interface ProductRepository {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product catToy);

    void delete(Product catToy);
}
