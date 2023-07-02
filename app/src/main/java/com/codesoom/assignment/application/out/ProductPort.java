package com.codesoom.assignment.application.out;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductPort {

    public List<Product> findAll();

    public Product findById(Long id);

    public Product save(Product product);

    public void delete(Product product);
    
}
