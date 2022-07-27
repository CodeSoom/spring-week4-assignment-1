package com.codesoom.assignment.domain;

import java.util.List;

/**
 * 고양이 장난감 정보를 관리하는 역할을 합니다.
 * 추가, 삭제, 조회, 전체 조회 기능을 사용할 수 있습니다.
 */
public interface CatToyRepository {
    /**
     * 저장된 모든 장난감을 조회합니다.
     * @return 저장된 장난감 목록
     */
    List<CatToy> findAll();

    /**
     * 모든 장난감 정보를 삭제합니다.
     */
    void deleteAll();

    /**
     * 장난감 정보를 저장합니다.
     * @param catToy 저장할 장난감
     * @return 저장된 장난감
     */
    CatToy save(CatToy catToy);
}
