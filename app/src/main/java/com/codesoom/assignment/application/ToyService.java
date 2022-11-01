package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;

import java.util.List;

public interface ToyService {
    List<Toy> getToys();

    Toy getToy(Long id);

    Toy create(Toy toy);

    Toy update(Long id, Toy src);

    void delete(Long id);
}
