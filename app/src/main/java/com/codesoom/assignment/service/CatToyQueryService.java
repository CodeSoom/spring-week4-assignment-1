package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.repository.CatToyRepository;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 장난감 조회 기능을 담당합니다.
 */
@Service
@Transactional
public class CatToyQueryService {

    private final CatToyRepository catToyRepository;

    public CatToyQueryService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    /**
     * 장난감 리스트를 리턴합니다.
     *
     * @return 장난감 리스트
     */
    public List<CatToy> getCatToyList() {
        return catToyRepository.findAll();
    }

    /**
     * 조회된 장난감을 리턴합니다.
     *
     * @param id 장난감 식별자
     * @return 장난감
     */
    public CatToy getCatToy(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(Long.toString(id)));
    }
}
