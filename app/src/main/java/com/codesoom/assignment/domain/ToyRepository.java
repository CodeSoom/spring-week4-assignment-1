package com.codesoom.assignment.domain;

import com.codesoom.assignment.ProductNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ToyRepository {
    private final List<Toy> toys = new ArrayList<>();
    private Long newId = 0L;

    public List<Toy> findAll() {
        return toys;
    }

    public Toy find(Long id) {
        return toys.stream()
                .filter(toy -> toy.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Toy save(Toy toy) {
        toy.setId(generatedId());

        toys.add(toy);

        return toy;
    }

    public Toy remove(Toy toy) {
        toys.remove(toy);
        return toy;
    }

    public Long generatedId() {
        newId += 1;
        return newId;
    }
}
