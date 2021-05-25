package com.codesoom.assignment.application;


import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


//TODO Exception 생성 및 교체 할 것


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
        return toyRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public CatToy createToy(CatToy catToy) {
        return toyRepo.save(catToy);
    }

    public CatToy updateToy(Long id, CatToy source) {
        CatToy toy = toyRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return toyRepo.updateToy(source, toy);
    }

    public void deleteToy(Long id) {
        CatToy toy = toyRepo.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        toyRepo.delete(toy);
    }
}
