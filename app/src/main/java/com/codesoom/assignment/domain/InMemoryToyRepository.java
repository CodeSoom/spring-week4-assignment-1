package com.codesoom.assignment.domain;

import com.codesoom.assignment.interfaces.ToyRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class InMemoryToyRepository implements ToyRepository {
    private final Map<Long, Toy> toy_map = new HashMap<>();
    private Long maxId = 1L;

    @Override
    public List<Toy> findAll() {
        return new ArrayList<>(toy_map.values());
    }

    @Override
    public Optional<Toy> findById(Long id) {
        return toy_map.values().stream()
            .filter(toy -> Objects.equals(toy.id(), id))
            .findFirst();
    }

    @Override
    public Toy save(Toy toy) {
        Toy toySaving = new Toy(idGenerated(), toy.name(), toy.producer(), toy.price());
        toy_map.put(toySaving.id(), toySaving);

        return toySaving;
    }

    @Override
    public void deleteById(Long id) {
        toy_map.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return toy_map.values().stream()
            .noneMatch(task -> Objects.equals(task.id(), id));
    }

    private Long idGenerated() {
        maxId++;
        return maxId;
    }
}
