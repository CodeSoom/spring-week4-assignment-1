package com.codesoom.assignment.application.in;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;

import java.util.List;

public interface ProductUseCase {

    public List<Product> getProducts();

    public Product getProduct(Long id);

    public Product createProduct(Product product);

    public Product updateProduct(Long id, Product source);

    public void deleteProduct(Long id);
    
}
