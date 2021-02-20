package com.codesoom.assignment.services;

import com.codesoom.assignment.exceptions.ToyNotFoundException;
import com.codesoom.assignment.models.Toy;
import com.codesoom.assignment.repositories.ToyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CatToyService implements ToyService {
    private final ToyRepository toyRepository;

    public CatToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    @Override
    public List<Toy> find() {
        return toyRepository.findAll();
    }

    @Override
    public Toy find(Long id) {
        return toyRepository
                .findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));
    }

    @Override
    public void insert(Toy toy) {
        toyRepository.save(toy);
    }

    @Override
    public void modify(Long id, Toy toy) {
        this.find(id).modify(toy);
    }

    @Override
    public void delete(Long id) {
        this.find(id);

        toyRepository.deleteById(id);
    }
}
