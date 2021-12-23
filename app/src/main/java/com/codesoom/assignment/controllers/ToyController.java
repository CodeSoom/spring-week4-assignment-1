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

    public Toy create(Toy source) {
        Toy toy = new Toy();

        toy.setId(generatedId());
        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImage(source.getImage());

        toys.add(toy);

        return toy;
    }

    public Toy product(Long id) {
        return toys.stream()
                .filter(toy -> toy.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(Long id) {
        Toy toy = this.product(id);
        toys.remove(toy);
    }

    public Long generatedId() {
        newId += 1;
        return newId;
    }
}
