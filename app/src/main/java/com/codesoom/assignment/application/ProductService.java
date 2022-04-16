package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }

    public Product removeProduct(Product product) {
        repository.delete(product);

        return product;
    }

    public Product updateProduct(Long id, ProductDto source) {
        Product product = getProduct(id);

        product.setPrice(source.getPrice());
        product.setImage(source.getImage());
        product.setMaker(source.getMaker());
        product.setName(source.getName());

        return product;
    }
}
