package com.codesoom.assignment.service;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository toyProductRepository;

    public ProductServiceImpl(ProductRepository toyProductRepository) {

        this.toyProductRepository = toyProductRepository;

    }

    @Override
    public Product register(Product product) {

       return toyProductRepository.save(product);

    }

    @Override
    public Product getProduct(Long id) {

        return toyProductRepository.findById(id).orElseThrow();

    }

    @Override
    public List<Product> getProducts() {

        return toyProductRepository.findAll();

    }

    @Override
    public Product updateProduct(Long id, Product product) {

        Product updateProduct = getProduct(id);
        updateProduct.setProduct(product);

        return updateProduct;

    }

    @Override
    public void delete(Long id) {

        Optional<Product> foundProduct = toyProductRepository.findById(id);

        toyProductRepository.deleteById(foundProduct.get().getId());

    }

}
