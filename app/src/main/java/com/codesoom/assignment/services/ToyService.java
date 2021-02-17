package com.codesoom.assignment.services;

import com.codesoom.assignment.models.Toy;

import java.util.List;

public interface ToyService {
    List<Toy> find();

    Toy find(long id);

    void insert(Toy toy);

    void modify(long id, Toy toy);

    void delete(long id);
}
