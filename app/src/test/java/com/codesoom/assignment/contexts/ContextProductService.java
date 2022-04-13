package com.codesoom.assignment.contexts;

import com.codesoom.assignment.repositories.ProductRepository;
import com.codesoom.assignment.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class ContextProductService extends ContextProduct{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    protected ProductService productService;

}
