package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ToyService {
    private final ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    public List<Toy> getToys() {
        return this.toyRepository.findAll();
    }

    public Toy getToy(Long id) {
        return this.toyRepository.findById(id).orElseThrow();
    }

    public Toy saveToy(Toy source) {
        return this.toyRepository.save(source);
    }

    public Toy updateToy(Long id, Toy source) {
        return this.toyRepository.findById(id).map(it -> {
            it.setContent(source);
            return it;
        }).orElseThrow();
    }

    public void deleteToy(Long id) {
        Optional<Toy> toy = this.toyRepository.findById(id);

        if (toy.isEmpty()) {
            throw new NoSuchElementException();
        }

        this.toyRepository.deleteById(id);
    }
}
