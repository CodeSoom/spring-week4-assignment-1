package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductDto;
import com.codesoom.assignment.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class ProductUpdateServiceImpl implements ProductUpdateService {

    private final ProductRepository repository;

    @Transactional
    @Override
    public Product update(Long id, ProductDto productDto) {
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        product.update(productDto.product());
        return product;
    }

}
