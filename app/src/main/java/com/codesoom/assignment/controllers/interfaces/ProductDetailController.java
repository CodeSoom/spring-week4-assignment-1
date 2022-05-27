package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyResponseDto;

/**
 * Product 타입에 대해 HTTP GET의 상세 조회 요청을 받고, 처리결과를 응답으로 반환한다
 * <p>
 * All Known Implementing Classes:
 * ToyDetailController
 * </p>
 */
public interface ProductDetailController {
    /**
     * 상세 조히 요청에 따른 처리 결과를 ToyResponseDto 형태로 가공하여 반환한다
     * <p>
     *
     * @param id Request Path Parameter 전달된 Toy Id를 받기 위한 객체
     * @return HTTP Request를 처리한 결과를 JSON 객체로 역직렬화하기 위한 객체
     * </p>
     */
    ToyResponseDto detail(Long id);
}
