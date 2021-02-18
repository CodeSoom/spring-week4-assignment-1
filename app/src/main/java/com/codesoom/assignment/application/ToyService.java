package com.codesoom.assignment.application;

import com.codesoom.assignment.ToyNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService {
    private ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    public List<Toy> getToys() {
        return toyRepository.findAll();
    }

    public Toy getToy(Long id) {
        return toyRepository.findById(id).orElseThrow(() -> new ToyNotFoundException(id));
    }

    public Toy createToy(Toy toy) {
        return toyRepository.save(toy);
    }

    public Toy updateToy(Toy toy) {
        final Long id = toy.getId();
        Toy foundToy = toyRepository.findById(id).orElseThrow(() -> new ToyNotFoundException(id));
        foundToy.setName(toy.getName());
        foundToy.setBrand(toy.getBrand());
        foundToy.setPrice(toy.getPrice());
        foundToy.setImageUrl(toy.getImageUrl());

        return foundToy;
    }

    public void deleteToy() {

    }
}
