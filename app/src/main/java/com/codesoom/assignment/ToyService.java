package com.codesoom.assignment;

import java.util.List;

public class ToyService {
    private ToyRepository toyRepository;

    public ToyService() {
        toyRepository = new ToyRepository();
    }

    public Toy getToyById(long id) {
        return toyRepository.findById(id);
    }

    public Toy register(Toy toy) {
        Toy savedToy = toyRepository.save(toy);
        return savedToy;
    }

    public List<Toy> getToys() {
        return toyRepository.findAll();
    }
}
