package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.services.ToyService;

import java.util.List;

public class ToyController {
    private ToyService toyService = new ToyService();

    public List<Toy> products() {
        return toyService.getProducts();
    }

    public Toy create(Toy toy) {
        return toyService.createProduct(toy);
    }

    public Toy product(Long id) {
        return toyService.getProduct(id);
    }

    public void delete(Long id) {
        toyService.deleteProduct(id);
    }
}
