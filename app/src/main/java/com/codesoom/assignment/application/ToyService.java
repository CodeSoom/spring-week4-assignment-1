package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ToyService {
    private final ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    public List<Toy> getProducts() {
        return toyRepository.findAll();
    }

    public Toy getProduct(Long id) {
        return toyRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Toy deleteProduct(Long id) {
        Toy toy = toyRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        toyRepository.delete(toy);

        return toy;
    }

    public Toy createProduct(Toy source) {
        Toy toy = new Toy();

        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImage(source.getImage());

        return toyRepository.save(toy);
    }

    public Toy updateProduct(Long id, Toy source) {
        Toy toy = toyRepository.findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));

        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setImage(source.getImage());
        toy.setPrice(source.getPrice());

        return toy;
    }
}
