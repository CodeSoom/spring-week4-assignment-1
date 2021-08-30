package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatToyService {
    private final CatToyRepository catToyRepository;

    public List<CatToy> findAll() {
        return null;
    }

    public CatToy findById(Long id) {
        return null;
    }

    public CatToy updateCatToy(long id, CatToy target) {
        return null;
    }

    public CatToy save(CatToy catToy) {
        return null;
    }
}
