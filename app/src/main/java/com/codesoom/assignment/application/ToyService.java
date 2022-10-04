package com.codesoom.assignment.application;

import com.codesoom.assignment.repository.ToyRepository;
import org.springframework.stereotype.Service;

@Service
public class ToyService{
    private final ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

}
