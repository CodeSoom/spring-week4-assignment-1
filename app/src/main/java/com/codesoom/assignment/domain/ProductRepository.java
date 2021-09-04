package com.codesoom.assignment.domain;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import com.codesoom.assignment.application.ProductService;

import java.util.List;

/**
 * Product 리소스관련 서비스 개체에게서 위임받은 작업을 수행한다.
 *
 * @see ProductService Product 리소스 생성, 수정, 삭제, 조회 작업을 위임힌다.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
}
