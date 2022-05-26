package com.codesoom.assignment.service;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ToyStoreRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ToyStoreService {

    private ToyStoreRepository toyStoreRepository;

    public ToyStoreService(ToyStoreRepository toyStoreRepository) {
        this.toyStoreRepository = toyStoreRepository;
    }

    public Product save(Product product) {
        return toyStoreRepository.save(product);
    }

    public List<Product> getProducts() {
        return toyStoreRepository.findAll();
    }

    public Product getProduct(long productId) {
        return toyStoreRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public Product deleteProduct(long productId) {
        Product product = getProduct(productId);

        toyStoreRepository.delete(product);

        return product;
    }

    public Product updateProduct(Product sourceProduct) {
        getProduct(sourceProduct.getId());

        Product product = new Product(sourceProduct.getId(), sourceProduct.getName(), sourceProduct.getMaker(),
                sourceProduct.getPrice(),
                sourceProduct.getImageUrl());

        return toyStoreRepository.save(product);
    }
}
