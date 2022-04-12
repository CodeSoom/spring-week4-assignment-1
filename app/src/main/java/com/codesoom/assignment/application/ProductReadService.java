package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductReadService {

    List<Product> findAll();

    Product findById(Long id);

}
