package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 고양이 장난감을 관리한다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatToyService implements ToyService<CatToy>{
    private final CatToyRepository catToyRepository;

    @Override
    public List<CatToy> findAll() {
        return catToyRepository.findAll();
    }

    @Override
    public CatToy findById(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));
    }

    @Override
    @Transactional
    public CatToy updateCatToy(Long id, CatToy target) {
        final CatToy catToy = findById(id);
        catToy.update(target);

        return catToy;
    }

    @Override
    public CatToy save(CatToy request) {
        final CatToy catToy = CatToy.from(request);
        return catToyRepository.save(catToy);
    }

    @Override
    public void deleteToy(Long id) {
        CatToy catToy = findById(id);

        catToyRepository.delete(catToy);
    }
}
