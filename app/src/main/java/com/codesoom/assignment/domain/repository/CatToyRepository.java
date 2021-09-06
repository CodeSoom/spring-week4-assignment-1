package com.codesoom.assignment.domain.repository;

import com.codesoom.assignment.domain.CatToy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 장난감을 조회, 삭제하는 Repository 입니다.
 */
@Repository
public interface CatToyRepository extends JpaRepository<CatToy, Long> {

    /**
     * 장난감을 등록합니다.
     *
     * @param catToy 등록할 장난감
     * @return 등록된 장난감
     */
    CatToy save(CatToy catToy);

    /**
     * 장난감을 조회합니다.
     *
     * @param id 장난감 식별자
     * @return 조회한 장난감
     */
    Optional<CatToy> findById(Long id);

    /**
     * 장난감 리스트를 조회합니다.
     *
     * @return 장난감 리스트
     */
    List<CatToy> findAll();

    /**
     * 장난감을 삭제합니다.
     *
     * @param id 삭제할 장난감 식별자
     */
    void deleteById(Long id);

    /**
     * 장난감을 모두 삭제합니다.
     */
    void deleteAll();
}
