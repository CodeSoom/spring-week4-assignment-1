package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{

    private ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll(){
        return repository.findAll();
    }

    public Optional<Product> findById(Long id){
        return repository.find(id);
    }

    public Product save(Product product){
        return repository.save(product);
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
