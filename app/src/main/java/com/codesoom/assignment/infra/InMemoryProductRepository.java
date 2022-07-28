package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private final HashMap<Long, Product> catToyHashMap = new HashMap<>();

    private Long newId = 0L;

    private Long generateId(){
        newId += 1;
        return newId;
    }
    @Override
    public Collection<Product> findAll(){
        return catToyHashMap.values();
    }

    @Override
    public Optional<Product> findById(Long id){
        return products.stream()
                .filter(catToy -> catToy.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product save(Product toy){
        toy.setId(generateId());
        products.add(toy);
        return toy;
    }

    @Override
    public void delete(Product toy) {
        products.remove(toy);
    }
}
