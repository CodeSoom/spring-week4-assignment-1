package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class ProductCreateServiceImpl implements ProductCreateService {

    private final ProductRepository repository;

    @Override
    public Product create(ProductDto productDto) {
        return repository.save(productDto.toEntity());
    }

}
