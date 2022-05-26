package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;

import java.util.List;

/**
 * Product 타입에 대해 HTTP GET의 목록 조회 요청을 받고, 처리결과를 응답으로 반환
 * <p>
 * All Known Implementing Classes:
 * ToyListController
 * </p>
 */
public interface ProductListController {
    /**
     * 목록 조회 요청에 따른 처리 결과를 List<ToyResponseDto> 형태로 가공하여 반환
     * <p>
     * @return  HTTP Request를 처리한 결과를 JSON 객체로 역직렬화하기 위한 객체
     * </p>
     */
    List<ToyResponseDto> list();
}
