package com.codesoom.assignment.contexts;

import com.codesoom.assignment.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ContextProductService extends ContextProductRepository {

    @Autowired
    protected ProductService productService;

}
