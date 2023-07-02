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

    public List<Product> findAll() {
        return productSpringDataRepository.findAll().stream().map(p -> ProductMapper.toDomain(p)).collect(Collectors.toList());
    }

    public Product findById(Long id) {
        ProductEntity productEntity = productSpringDataRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productSpringDataRepository.save(ProductMapper.toEntity(product));
        return ProductMapper.toDomain(productEntity);
    }

    @Override
    public void delete(Product product) {
        productSpringDataRepository.delete(ProductMapper.toEntity(product));
    }

}

