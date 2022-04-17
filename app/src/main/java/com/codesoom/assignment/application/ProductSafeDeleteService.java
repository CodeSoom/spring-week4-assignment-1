package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 존재하는 상품만 삭제합니다.
 */
@RequiredArgsConstructor
@Service
public class ProductSafeDeleteService implements ProductDeleteService {

    private final ProductRepository repository;

    @Transactional
    @Override
    public void deleteById(Long id) {
        final Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        repository.delete(product);
    }

}
