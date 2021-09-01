package com.codesoom.assignment.applications;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.dto.CatToyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatToyService {

    private CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    CatToy save(CatToy catToy) {
        return catToyRepository.save(catToy);
    }

    List<CatToy> findAll() {
        return catToyRepository.findAll();
    }

    CatToy findById(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(()->new CatToyNotFoundException(id));
    }

    void update(Long id, CatToy catToy) {
        catToyRepository.update(id, catToy);
    }

    void deleteById(Long id) {
        catToyRepository.deleteById(id);
    }


}
