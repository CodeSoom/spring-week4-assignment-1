package com.codesoom.assignment.services;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Toy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ToyService {
    private List<Toy> toys = new ArrayList<>();
    private Long newId = 0L;

    public List<Toy> getProducts() {
        return toys;
    }

    public Toy getProduct(Long id) {
        return toys.stream()
                .filter(toy -> toy.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Toy deleteProduct(Long id) {
        Toy toy = getProduct(id);
        toys.remove(toy);

        return toy;
    }

    public Toy createProduct(Toy source) {
        Toy toy = new Toy();

        toy.setId(generatedId());
        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImage(source.getImage());

        toys.add(toy);

        return toy;
    }

    public Long generatedId() {
        newId += 1;
        return newId;
    }
}
