package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.dto.ToySaveRequest;
import com.codesoom.assignment.repository.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService {

    private ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    public List<Toy> list() {
        return (List<Toy>) toyRepository.findAll();
    }

    public Toy create(Toy toy) {
        return this.toyRepository.save(toy);
    }
}
