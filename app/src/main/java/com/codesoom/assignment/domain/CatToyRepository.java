package com.codesoom.assignment.domain;

import com.codesoom.assignment.ToyNotFoundException;

import java.util.List;
import java.util.Optional;

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
     * id로 저장된 장난감을 찾습니다.
     * @param catToyId 고양이 장난감 식별자
     * @return 찾은 장난감을 반환합니다.
     */
    Optional<CatToy> findById(Long catToyId);

    /**
     * 요청한 Id의 장난감을 삭제합니다.
     * @param toyId 장난감 식별자
     */
    void deleteById(Long toyId);

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

    /**
     * 장난감 정보를 업데이트 합니다.
     * @param toyId 장난감 식별자
     * @param newToy 새로운 장난감
     * @return 업데이트 된 장난감
     */
    default CatToy update(Long toyId, CatToy newToy) {
        CatToy result = findById(toyId)
                .orElseThrow(() -> new ToyNotFoundException(toyId));

        result.setName(newToy.getName());
        result.setMaker(newToy.getMaker());
        result.setPrice(newToy.getPrice());
        result.setImageURL(newToy.getImageURL());

        return result;
    }
}
