package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Collection;
import java.util.Optional;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    //private final List<Product> products = new ArrayList<>();
    private final HashMap<Long, Product> productHashMap = new HashMap<>();

    private Long newId = 0L;

    private Long generateId(){
        newId += 1;
        return newId;
    }
    @Override
    public Collection<Product> findAll(){
        return productHashMap.values();
    }

    @Override
    public Optional<Product> findById(Long id){
        return Optional.of(productHashMap.get(id));
    }

    @Override
    public Product save(Product product){

        product.setId(generateId());
        productHashMap.put(product.getId(), product);
        return product;
    }

    @Override
    public void delete(Product toy) {
        productHashMap.remove(toy);
    }
}
