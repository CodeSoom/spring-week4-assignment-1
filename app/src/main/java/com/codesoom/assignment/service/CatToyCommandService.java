package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.repository.CatToyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 장난감 생성, 수정 삭제 기능을 담당합니다.
 */
@Service
@Transactional
public class CatToyCommandService {

    private final CatToyRepository catToyRepository;

    public CatToyCommandService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    /**
     * 새로운 장난감을 등록합니다.
     *
     * @param catToy 등록할 장난감
     * @return 등록된 장난감
     */
    public CatToy createCatToy(CatToy catToy) {
        return null;
    }

    /**
     * 장난감 정보를 수정합니다.
     *
     * @param id 수정될 장난감 식별자
     * @param updateCatToy 수정될 장난감 정보
     * @return 수정된 장난감
     */
    public CatToy updateCatToy(Long id, CatToy updateCatToy) {
        return null;
    }

    /**
     * 장난감을 삭제합니다.
     *
     * @param id 삭제할 장난감 식별자
     */
    public void deleteCatToy(Long id) {

    }
}
