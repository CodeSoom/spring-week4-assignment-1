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

    public Toy register(String name, String maker, int price, String imageUrl) {
        Toy toy = toyRepository.save(new Toy(null, name, maker, price, imageUrl));
        return toy;
    }

    public List<Toy> getToys() {
        return toyRepository.findAll();
    }
}
