package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyResponseDto;

/**
 * Product 타입에 대해 HTTP DELETE 요청을 받아서 처리
 * <p>
 * All Known Implementing Classes:
 * ToyDeleteController
 * </p>
 */
public interface ProductDeleteController {
    /**
     * 삭제 요청에 따른 처리
     * <p>
     *
     * @param id Request Path Parameter 전달된 Toy Id를 받기 위한 객체
     * </p>
     */
    void delete(Long id);
}
