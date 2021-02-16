package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService {
    private ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    public List<Toy> getToys() {
        return toyRepository.findAll();
    }

    public Toy getToy() {
        return null;
    }

    public Toy createToy() {
        return null;
    }

    public Toy updateToy() {
        return null;
    }

    public void deleteToy() {

    }
}
