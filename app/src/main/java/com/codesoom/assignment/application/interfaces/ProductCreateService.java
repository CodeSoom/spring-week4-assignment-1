package com.codesoom.assignment.application.interfaces;

import com.codesoom.assignment.domain.Toy;

/**
 * 상품 타입 생성에 대한 비지니스 로직을 처리한다
 * <p>
 * All Known Implementing Classes:
 * ToyCreateService
 * </p>
 */
public interface ProductCreateService {
    /**
     * Toy 엔티티 객체 생성하고, 생성된 객체를 반환한다.
     * <p>
     * @param toy Toy 객체
     * @return Toy 객체
     * </p>
     */
    Toy create(Toy toy);
}
