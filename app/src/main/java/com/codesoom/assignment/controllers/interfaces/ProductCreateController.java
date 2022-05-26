package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyRequestDto;
import com.codesoom.assignment.controllers.dtos.ToyResponseDto;

/**
 * Toy 객체에 연관된 HTTP POST 요청을 받고, 처리결과를 응답으로 반환
 * <p>
 * All Known Implementing Classes:
 * ToyCreateController
 * </p>
 */
public interface ProductCreateController {
    /**
     * POST 요청에 따른 처리 결과를 ToyResponseDto 형태로 가공하여 반환
     * <p>
     * @param requestDto Request Body로 전달된 JSON 객체를 직렬화하여 받기 위한 객체
     * @return HTTP Request를 처리한 결과를 JSON 객체로 역직렬화하기 위한 객체
     * </p>
     */
    ToyResponseDto create(ToyRequestDto requestDto);
}
