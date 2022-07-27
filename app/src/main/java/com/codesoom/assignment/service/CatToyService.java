package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CatToyService implements ToyService {
    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public CatToy create(CatToyDto catToyDto) {
        return catToyRepository.save(catToyDto.toCatToy());
    }

    public CatToy findById(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException("id " + id + "을 가진 장난감을 찾을 수 없습니다."));
    }
}
