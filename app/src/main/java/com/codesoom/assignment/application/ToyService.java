package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ToyRepository;
import com.codesoom.assignment.error.NotFoundException;
import com.codesoom.assignment.interfaces.ProductRepository;
import com.codesoom.assignment.interfaces.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToyService implements ProductService {
    private final ProductRepository productRepository;

    public ToyService(ToyRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

//    @Override
//    public Product updateProduct(Long id, Product product) {
//        return null;
//    }
//
//    @Override
//    public Product deleteProduct(Product product) {
//        return null;
//    }
}
