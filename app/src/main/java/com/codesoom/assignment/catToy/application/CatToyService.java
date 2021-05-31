package com.codesoom.assignment.catToy.application;


import com.codesoom.assignment.catToy.ToyNotFoundException;
import com.codesoom.assignment.catToy.domain.CatToy;
import com.codesoom.assignment.catToy.domain.CatToyRepository;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CatToyService {
    private final CatToyRepository toyRepo;

    public CatToyService(CatToyRepository toyRepo) {
        this.toyRepo = toyRepo;
    }

    public List<CatToy> getToys() {
        return toyRepo.findAll();
    }

    public CatToy getDetailToy(Long id) {
        return findById(id);
    }

    public CatToy createToy(CatToy catToy) {
        return toyRepo.save(catToy);
    }

    public CatToy updateToy(Long id, CatToy source) {
        CatToy toy = findById(id);
        return toyRepo.updateToy(source, toy);
    }

    public void deleteToy(Long id) {
        CatToy toy = findById(id);
        toyRepo.delete(toy);
    }

    private CatToy findById(Long id) {
        return toyRepo.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));
    }
}
