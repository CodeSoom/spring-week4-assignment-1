package com.codesoom.service;

import com.codesoom.domain.Toy;

import java.util.List;
import java.util.Optional;

public interface ToyService {

    Toy register(Toy toy);

    Optional<Toy> getToy(Long id);

    List<Toy> getToys();

    void delete(Toy toy);

}
