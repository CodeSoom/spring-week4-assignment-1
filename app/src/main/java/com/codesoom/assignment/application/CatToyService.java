package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 고양이 장난감에 대한 요청을 처리합니다.
 */
@Service
public class CatToyService {

    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    /**
     *  고양이 장난감 전체 목록을 리턴합니다.
     */
    public List<CatToy> getCatToys() {
        Iterable<CatToy> source = catToyRepository.findAll();
        List<CatToy> catToys = new ArrayList<>();
        source.forEach(catToys::add);
        return catToys;
    }
}
