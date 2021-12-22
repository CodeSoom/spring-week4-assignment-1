package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;

import java.util.ArrayList;
import java.util.List;

public class ToyController {
    private List<Toy> toys = new ArrayList<>();
    private Long newId = 0L;

    public List<Toy> products() {
        return toys;
    }

    public Toy create(Toy toy) {
        toy.setId(generatedId());
        toys.add(toy);

        return toy;
    }

    public Toy product(Long id) {
        return toys.stream()
                .filter(toy -> toy.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Long generatedId() {
        newId += 1;
        return newId;
    }
}
