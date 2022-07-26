package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

@Service
public class CatToyService implements ToyService {
    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    /**
     * 장난감을 생성한다.
     *
     * @param catToyDto 고양이 장난감 Dto
     * @return 고양이 장난감
     */
    public CatToy create(CatToyDto catToyDto) {
        return catToyRepository.save(catToyDto.toCatToy());
    }
}
