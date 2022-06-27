package com.codesoom.assignment;

public class ToyService {
    private ToyRepository toyRepository;

    public ToyService() {
        toyRepository = new ToyRepository();
    }

    public Toy getToyById(long id) {
        return toyRepository.findById(id);
    }

    public Toy register(String name, String maker, int price, String imageUrl) {
        toyRepository.save(new Toy(null, name, maker, price, imageUrl));
        return new Toy(1L, name, maker, price, imageUrl);
    }
}
