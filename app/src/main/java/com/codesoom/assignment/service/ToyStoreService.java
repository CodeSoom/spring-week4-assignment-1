package com.codesoom.assignment.service;

import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ToyStoreRepository;
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
}
