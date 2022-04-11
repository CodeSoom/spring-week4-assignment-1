package com.codesoom.assignment.application;

import com.codesoom.assignment.CatToyNotFoundException;
import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatToyService {
    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<CatToy> getCatToys() {

        return catToyRepository.findAll();
    }

    public CatToy getCatToy(Long id) {

        return catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));
    }

    public CatToy createCatToy(CatToy source) {
        CatToy catToy = new CatToy();

        catToy.setName(source.getName());
        catToy.setMaker(source.getMaker());
        catToy.setPrice(source.getPrice());
        catToy.setImagePath(source.getImagePath());

        return catToyRepository.save(catToy);
    }

    public CatToy updateCatToy(Long id, CatToy source) {
        CatToy catToy = catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));

        catToy.setName(source.getName());
        catToy.setMaker(source.getMaker());
        catToy.setPrice(source.getPrice());
        catToy.setImagePath(source.getImagePath());

        return catToy;
    }

    public CatToy deleteCatToy(Long id) {
        CatToy catToy = catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));

        catToyRepository.delete(catToy);

        return catToy;
    }
}
