package com.codesoom.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ToyRepository {
    private AtomicLong idCounter = new AtomicLong(); // default value is 0
    Map<Long, Toy> toys = new ConcurrentHashMap();

    public Optional<Toy> findById(long id) {
        return Optional.ofNullable(toys.get(id));
    }

    public Toy save(Toy toy) {
        Toy toyEntity = new Toy(nextId(), toy.getName(), toy.getMaker(), toy.getPrice(), toy.getImageUrl());
        toys.put(toyEntity.getId(), toyEntity);

        return toyEntity;
    }

    public List<Toy> findAll() {
        return new ArrayList<>(toys.values());
    }

    public Optional<Toy> deleteById(Long id) {
        return Optional.ofNullable(toys.remove(id));
    }

    private long nextId() {
        return idCounter.incrementAndGet();
    }
}
