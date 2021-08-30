package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatToyService {
    private final CatToyRepository catToyRepository;

    public List<CatToy> findAll() {
        return catToyRepository.findAll();
    }

    public CatToy findById(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(()-> new CatToyNotFoundException(id));
    }

    @Transactional
    public CatToy updateCatToy(Long id, CatToy target) {
        final CatToy catToy = findById(id);
        catToy.update(target);

        return catToy;
    }

    public CatToy save(CatToy catToy) {
        return catToyRepository.save(catToy);
    }

    public void deleteCatToy(Long id) {
        CatToy catToy = findById(id);

        catToyRepository.delete(catToy);
    }
}
