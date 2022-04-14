package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

/**
 * 고객이 입력한 정보를 엔티티로 변환하는 역할을 담당합니다.
 */
public interface ProductSaveRequest {

    Product toEntity();

}
