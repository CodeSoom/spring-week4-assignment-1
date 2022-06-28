package com.codesoom.assignment;

import java.util.ArrayList;
import java.util.List;

public class ToyRepository {
    List<Toy> toys = new ArrayList<>();


    public Toy findById(long id) {
        for (Toy toy : toys) {
            if (toy.getId() == id) {
                return toy;
            }
        }

        return null;
    }

    public Toy save(Toy toy) {
        toy.setId(nextId());
        toys.add(toy);

        return toy;
    }

    public List<Toy> findAll() {
        return toys;
    }

    private long nextId() {
        return toys.size() + 1;
    }
}
