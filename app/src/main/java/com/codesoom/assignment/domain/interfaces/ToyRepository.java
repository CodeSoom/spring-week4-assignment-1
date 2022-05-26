package com.codesoom.assignment.domain.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;
import java.util.Optional;

/**
 * Toy 엔티티에 대한 영속적인 CRUD 작업
 * <p>
 * All Known Implementing Classes:
 * InMemoryToyRepository
 * </p>
 */
public interface ToyRepository {
    /**
     * 모든 Toy 객체를 List의 형태로 불러온다
     * <p>
     * @return Toy 엔티티 객체를 내부 요소로 하는 List Collection 객체
     * </p>
     */
    List<Toy> findAll();

    /**
     * 매개변수로 전달 받은 id에 해당하는 Toy 객체를 불러온다
     * <p>
     * @param id Toy의 Id에 해당
     * @return Optional<Toy> Toy 엔티티
     * </p>
     */
    Optional<Toy> findById(Long id);

    /**
     * Toy 엔티티 객체 저장
     * <p>
     * @param toy Toy 엔티티 객체
     * @return Toy 엔티티 객체
     * </p>
     */
    Toy save(Toy toy);

    /**
     * Toy 엔티티 객체 삭제
     * <p>
     * @param id Toy 객체의 Id에 해당하는 객체
     * </p>
     */
    void deleteById(Long id);

    /**
     * 매개변수로 전달 받은 id에 해당하는 Toy 객체의 존재 여부를 알려준다
     * <p>
     * @param id Toy 객체의 Id에 해당
     * @return id에 해당하는 Toy 객체가 존재하면 True, 존재하지 않으면 False를 반환
     * </p>
     */
    boolean existsById(Long id);
}
