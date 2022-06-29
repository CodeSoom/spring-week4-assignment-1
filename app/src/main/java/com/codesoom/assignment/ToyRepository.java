package com.codesoom.assignment;

import com.codesoom.assignment.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ToyRepository {
    Map<Long, Toy> toys = new ConcurrentHashMap();

    public Optional<Toy> findById(long id) {
        return Optional.ofNullable(toys.get(id));
    }

    public Toy save(Toy toy) {
        Toy toyEntity = new Toy(IdGenerator.next(), toy.getName(), toy.getMaker(), toy.getPrice(), toy.getImageUrl());
        toys.put(toyEntity.getId(), toyEntity);

        return toyEntity;
    }

    public Toy update(Toy toy) {
        toys.put(toy.getId(), toy);

        return toy;
    }

    public List<Toy> findAll() {
        return new ArrayList<>(this.toys.values()).stream()
                .sorted((t1, t2) -> t1.getId().compareTo(t2.getId()))
                .collect(Collectors.toList());
    }

    public Optional<Toy> deleteById(Long id) {
        return Optional.ofNullable(toys.remove(id));
    }
}
