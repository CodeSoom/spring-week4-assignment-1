package com.codesoom.assignment.services;

import com.codesoom.assignment.exceptions.ToyNotFoundException;
import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import com.codesoom.assignment.repositories.CatToyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatToyService implements ToyService {
    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    @Override
    public List<Toy> find() {
        return new ArrayList<>(catToyRepository.findAll());
    }

    @Override
    public Toy find(Long id) throws ToyNotFoundException {
        return catToyRepository
                .findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));
    }

    @Override
    public void insert(Toy toy) {
        catToyRepository.save((CatToy) toy);
    }

    @Override
    public void modify(Long id, Toy toy) throws ToyNotFoundException {
        this.find(id).modify(toy);
    }

    @Override
    public void delete(Long id) throws ToyNotFoundException {
        this.find(id);

        catToyRepository.deleteById(id);
    }
}
