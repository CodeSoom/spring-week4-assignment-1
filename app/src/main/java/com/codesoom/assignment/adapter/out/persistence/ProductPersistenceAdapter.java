package com.codesoom.assignment.adapter.out.persistence;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.out.ProductPort;
import com.codesoom.assignment.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ProductPort {

    private final ProductSpringDataRepository productSpringDataRepository;
    private final ProductMapper productMapper;

    public List<Product> findAll() {
        return productSpringDataRepository.findAll().stream().map(p -> productMapper.toDomain(p)).collect(Collectors.toList());
    }

    public Product findById(Long id) {
        ProductEntity productEntity = productSpringDataRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return productMapper.toDomain(productEntity);
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productSpringDataRepository.save(productMapper.toEntity(product));
        return productMapper.toDomain(productEntity);
    }

    @Override
    public void delete(Product product) {
        productSpringDataRepository.delete(productMapper.toEntity(product));
    }

}

