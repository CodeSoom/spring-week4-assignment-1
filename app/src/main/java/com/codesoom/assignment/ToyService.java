package com.codesoom.assignment;

import com.codesoom.assignment.exceptions.ToyNotFoundException;

import java.util.List;

public class ToyService {
    private ToyRepository toyRepository;

    public ToyService() {
        toyRepository = new ToyRepository();
    }

    public Toy getToyById(long id) throws ToyNotFoundException {
        return toyRepository.findById(id).orElseThrow(() -> new ToyNotFoundException(id));
    }

    public Toy register(Toy toy) {
        Toy savedToy = toyRepository.save(toy);
        return savedToy;
    }

    public List<Toy> getToys() {
        return toyRepository.findAll();
    }

    public Toy update(Long id, Toy newToy) throws ToyNotFoundException {
        Toy savedToy = toyRepository.findById(id).orElseThrow(() -> new ToyNotFoundException(id));

        newToy.setId(savedToy.getId());
        return toyRepository.save(newToy);
    }
}
