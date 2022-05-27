package com.codesoom.assignment.application.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

/**
 * Product 타입 수정에 대한 비지니스 로직
 * <p>
 * All Known Implementing Classes:
 * ToyUpdateService
 * </p>
 */
public interface ProductUpdateService {
    /**
     * 매개변수로 전달 받은 id에 해당하는 Toy 엔티티를 반영하여 수정된 Toy 엔티티 반환
     * <p>
     * @param id Toy 엔티티의 Id에 해당
     * @return 수정된 Toy 엔티티
     * </p>
     */
    Toy update(Long id, Toy toy);
}
