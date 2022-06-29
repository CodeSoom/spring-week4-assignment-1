package com.codesoom.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ToyRepository {
    List<Toy> toys = new ArrayList<>();

    public Optional<Toy> findById(long id) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                return Optional.of(toy);
            }
        }

        return Optional.empty();
    }

    public Toy save(Toy toy) {
        Toy toyEntity = new Toy(nextId(), toy.getName(), toy.getMaker(), toy.getPrice(), toy.getImageUrl());
        toys.add(toyEntity);

        return toyEntity;
    }

    public List<Toy> findAll() {
        return toys;
    }

    public Optional<Toy> deleteById(Long id) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                return Optional.of(toy);
            }
        }

        return Optional.empty();
    }

    private long nextId() {
        return toys.size() + 1;
    }
}
