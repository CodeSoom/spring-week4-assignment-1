package com.codesoom.assignment.services;

import com.codesoom.assignment.exceptions.ToyNotFoundException;
import com.codesoom.assignment.models.Toy;

import java.util.List;

public interface ToyService {
    List<Toy> find();

    Toy find(Long id) throws ToyNotFoundException;

    void insert(Toy toy);

    void modify(Long id, Toy toy) throws ToyNotFoundException;

    void delete(Long id) throws ToyNotFoundException;
}
