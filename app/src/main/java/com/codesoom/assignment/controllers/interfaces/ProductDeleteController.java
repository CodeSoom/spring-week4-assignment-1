package com.codesoom.assignment.controllers.interfaces;

import com.codesoom.assignment.controllers.dtos.ToyResponseDto;

/**
 * Product 타입에 대해 HTTP DELETE 요청을 수신한다
 * <p>
 * All Known Implementing Classes:
 * ToyDeleteController
 * </p>
 */
public interface ProductDeleteController {
    /**
     * 특정 '상품'에 대한 삭제 요청을 수신한다
     * <p>
     *
     * @param id Request Path Parameter 전달된 Toy Id를 받기 위한 객체
     * </p>
     */
    void delete(Long id);
}
