package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ToyService;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.exceptions.NegativeIdException;

import java.util.List;

public class ToyController {

    private final ToyService service;

    public ToyController(ToyService service) {
        this.service = service;
    }

    public List<Toy> getToys() {
        return service.getToys();
    }

    public Toy getToy(Long id) {
        if (id < 0) {
            throw new NegativeIdException();
        }

        return service.getToy(id);
    }
}
