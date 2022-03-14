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
        return findToy(id);
    }

    public Toy deleteProduct(Long id) {
        Toy toy = findToy(id);

        toyRepository.delete(toy);

        return toy;
    }

    public Toy createProduct(Toy source) {
        Toy toy = new Toy(
            source.getName(),
            source.getMaker(),
            source.getPrice(),
            source.getImage()
        );

        return toyRepository.save(toy);
    }

    public Toy updateProduct(Long id, Toy source) {
        Toy toy = findToy(id);

        toy.change(source);

        return toy;
    }

    private Toy findToy(Long id) {
        return toyRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
