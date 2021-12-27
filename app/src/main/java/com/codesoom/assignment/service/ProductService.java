package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productsRepository;

    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productsRepository, ModelMapper modelMapper) {
        this.productsRepository = productsRepository;
        this.modelMapper = modelMapper;
    }

    public List<Product> getProducts() {
        return productsRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productsRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product product) {
        return productsRepository.save(product);
    }

    public Product updateProduct(Long targetId, Product source) {
        Product product = getProduct(targetId);

        this.modelMapper.map(source, product);
        product.setId(targetId);

        return product;
    }

    public void deleteProduct(Long id) {
        Product product = getProduct(id);
        productsRepository.delete(product);
    }
}
