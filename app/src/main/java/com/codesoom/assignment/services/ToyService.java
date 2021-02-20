package com.codesoom.assignment.services;

import com.codesoom.assignment.models.Toy;

import java.util.List;

public interface ToyService {
    List<Toy> find();

    Toy find(Long id);

    void insert(Toy toy);

    void modify(Long id, Toy toy);

    void delete(Long id);
}
