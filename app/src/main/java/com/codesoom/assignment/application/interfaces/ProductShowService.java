package com.codesoom.assignment.application.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

/**
 * Product 타입 조회에 대한 비지니스 로직을 처리한다
 * <p>
 * All Known Implementing Classes:
 * ToyShowService
 * </p>
 */
public interface ProductShowService {
    /**
     * 모든 Toy 엔티티를 List 형태로 반환한다
     * <p>
     * @return Toy 엔티티를 내부 요소로 하는 List Collection
     * </p>
     */
    List<Toy> showAll();

    /**
     * 매개변수로 전달 받은 id에 해당하는 Toy 엔티티를 반환한다
     * <p>
     * @param id Toy 엔티티의 Id에 해당
     * @return Toy 엔티티
     * </p>
     */
    Toy showById(Long id);
}
