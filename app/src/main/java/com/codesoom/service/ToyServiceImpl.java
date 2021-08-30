package com.codesoom.service;

import com.codesoom.domain.Toy;
import com.codesoom.repository.ToyRepository;

import java.util.List;
import java.util.Optional;

public class ToyServiceImpl implements ToyService{

    private final ToyRepository toyRepository;

    public ToyServiceImpl(ToyRepository toyRepository) {

        this.toyRepository = toyRepository;

    }

    @Override
    public Toy register(Toy toy) {

        return toyRepository.save(toy);

    }

    @Override
    public Optional<Toy> getToy(Long id) {

        return toyRepository.findById(id);

    }

    @Override
    public List<Toy> getToys() {

        return toyRepository.findAll();

    }

    @Override
    public void delete(Toy toy) {

        toyRepository.delete(toy);

    }
}
