package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.ToyInfo;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatToyService implements ToyService {
    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public CatToy create(ToyInfo toyInfo) {
        return catToyRepository.save(toyInfo.toCatToy());
    }

    public CatToy findById(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException("id " + id + "을 가진 장난감을 찾을 수 없습니다."));
    }

    public List<CatToy> findAll() {
        return catToyRepository.findAll();
    }

    public CatToy update(Long id, ToyInfo dto) {
        CatToy catToy = findById(id);

        catToy.update(dto.getName(), dto.getMaker(), dto.getPrice(), dto.getImageUrl());

        return catToy;
    }

    public void deleteById(Long id) {
        findById(id);

        catToyRepository.deleteById(id);
    }
}
