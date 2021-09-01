package com.codesoom.assignment.cattoy.application;

import com.codesoom.assignment.common.application.ProductService;
import com.codesoom.assignment.cattoy.domain.CatToy;
import com.codesoom.assignment.cattoy.domain.CatToyRepository;
import com.codesoom.assignment.cattoy.exception.CatToyNotFoundException;
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
public class CatToyService implements ProductService<CatToy> {
    private final CatToyRepository catToyRepository;

    @Override
    public List<CatToy> findAll() {
        return catToyRepository.findAll();
    }

    @Override
    public CatToy findById(long id) {
        return catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));
    }

    @Override
    @Transactional
    public CatToy updateProduct(long id, CatToy target) {
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
    @Transactional
    public void deleteProduct(CatToy toy) {
        catToyRepository.delete(toy);
    }
}
