package com.codesoom.assignment.application;


import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.JpaToyRepository;
import com.codesoom.assignment.domain.Toy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


// @Service, public 메소드엔 Javadoc 주석 생성할 것
@Service
@Transactional
public class ToyService {

    private final JpaToyRepository jpaToyRepository;

    public ToyService(JpaToyRepository jpaToyRepository) {
        this.jpaToyRepository = jpaToyRepository;
    }

    public List<Toy> getAllToy(){
        return jpaToyRepository.findAll();
    }

    public Toy getToyById(Long id) {
        return jpaToyRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Toy createToy(Toy source) {
        Toy toy = new Toy();
        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());

        return jpaToyRepository.save(toy);
    }

    public Toy updateToy(Long id, Toy source) {

        Toy toy = jpaToyRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());

        return toy;
    }

    public void deleteToy(Long id) {
        Toy toy = jpaToyRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        jpaToyRepository.delete(toy);
    }
}
