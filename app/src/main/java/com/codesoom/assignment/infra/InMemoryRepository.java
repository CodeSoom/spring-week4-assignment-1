package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private Long id = 0L;

    @Override
    public List<Product> findAll(){
        return products;
    }

    @Override
    public Optional<Product> findById(Long id){
        return products.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public Product save(Product product){
        product.setId(generateId());

        products.add(product);
        return product;
    }

    @Override
    public void delete(Product product){
        products.remove(product);
    }

    private Long generateId() {
        id += 1;
        return id;
    }
}
