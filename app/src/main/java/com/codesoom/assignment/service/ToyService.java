package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.ToyInfo;

import java.util.List;

public interface ToyService {
    /**
     * 장난감을 생성한다.
     *
     * @param toyInfo 고양이 장난감 Dto
     * @return 고양이 장난감
     */
    CatToy create(ToyInfo toyInfo);

    /**
     * 장난감을 조회한다.
     *
     * @param id 식별자
     * @return 장난감
     */
    CatToy findById(Long id);

    /**
     * 장난감 목록을 조회한다.
     *
     * @return 장난감 목록
     */
    List<CatToy> findAll();

    /**
     * 주어진 식별자를 가진 장난감을 제거하고 삭제되었으면 true, 아니면 false 를 리턴한다.
     *
     * @param id 식별자
     */
    void deleteById(Long id);

    /**
     * 주어진 식별자를 가진 장난감을 주어진 변경 정보로 변경하고 리턴한다.
     *
     * @param id 식별자
     * @param dto 변경할 정보
     * @return 변경된 장난감
     */
    CatToy update(Long id, ToyInfo dto);
}
