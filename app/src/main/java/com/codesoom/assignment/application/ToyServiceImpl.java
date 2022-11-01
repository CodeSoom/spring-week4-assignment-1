package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import com.codesoom.assignment.exceptions.ToyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyServiceImpl implements ToyService {

    private final ToyRepository repository;

    public ToyServiceImpl(ToyRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Toy> getToys() {
        return repository.findAll();
    }

    @Override
    public Toy getToy(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));
    }

    @Override
    public Toy create(Toy toy) {
        return repository.save(toy);
    }

    @Override
    public Toy update(Long id, Toy src) {
        final Toy toy = repository.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));

        toy.update(src);

        return repository.save(toy);
    }

    @Override
    public void delete(Long id) {
        final Toy toy = repository.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));

        repository.delete(toy);
    }
}
