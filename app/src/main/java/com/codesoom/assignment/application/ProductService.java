package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product createProduct(Product sourceProduct) {
        Product product = new Product(sourceProduct);
        return productRepository.save(product);
    }


    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product updateProduct(Long id, Product sourceProduct) {
        Product updatedProduct = getProductById(id);
        updatedProduct.update(sourceProduct);
        return productRepository.save(updatedProduct);
    }

    public void delete(Long id) {
        try{
            productRepository.deleteById(id);
        }
        catch (EmptyResultDataAccessException e){
            throw new ProductNotFoundException(id);
        }
    }
}
