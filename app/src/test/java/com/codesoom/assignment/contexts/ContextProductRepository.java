package com.codesoom.assignment.contexts;

import com.codesoom.assignment.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class ContextProductRepository extends ContextProduct {

    @Autowired
    protected ProductRepository productRepository;

}
