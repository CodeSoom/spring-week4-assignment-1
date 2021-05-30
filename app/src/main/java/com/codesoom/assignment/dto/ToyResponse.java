package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Toy;

public class ToyResponse {
    
    public ToyResponse(Toy toy) {
        Long id = toy.getId();
        String name = toy.getName();
        String maker = toy.getMaker();
        Double price = toy.getPrice();
        String imageUrl = toy.getImageUrl();
    }
}
