package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    private final ProductRepository repository;

    @Transactional
    @Override
    public Product saveProduct(ProductSaveRequest saveRequest) {
        return repository.save(saveRequest);
    }

}
