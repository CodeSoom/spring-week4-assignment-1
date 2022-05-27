package com.codesoom.assignment.domain.interfaces;

import com.codesoom.assignment.domain.Toy;

import java.util.List;
import java.util.Optional;

/**
 * '장난감' 저장소 인터페이스
 * <p>
 * All Known Implementing Classes:
 * InMemoryToyRepository
 * </p>
 */
public interface ToyRepository {
    /**
     * 모든 장난감을 반환한다
     * <p>
     * @return 장난감 엔티티를 내부 요소로 하는 List 콜렉션
     * </p>
     */
    List<Toy> findAll();

    /**
     * id에 해당하는 장난감을 반환한다
     * <p>
     * @param id 장난감의 id
     * @return Optional<Toy> 장난감 타입
     * </p>
     */
    Optional<Toy> findById(Long id);

    /**
     * 장난감을 저장한다
     * <p>
     * @param toy Toy 엔티티 객체
     * @return Toy 엔티티 객체
     * </p>
     */
    Toy save(Toy toy);

    /**
     * 장난감을 삭제한다
     * <p>
     * @param id 장난감의 id
     * </p>
     */
    void deleteById(Long id);

    /**
     * id에 해당하는 장난감의 존재여부를 알려준다
     * <p>
     * @param id 장난감의 id
     * @return 장난감의 존재여부
     * </p>
     */
    boolean existsById(Long id);
}
