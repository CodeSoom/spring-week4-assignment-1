package com.codesoom.assignment.catToy.application;


import com.codesoom.assignment.task.TaskNotFoundException;
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
