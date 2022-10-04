package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 장난감을 찾지 못했습니다."));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Long id, Product source) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 장난감을 찾지 못해 업데이트할 수 없습니다."));

        product.update(source.getName(),
                source.getMaker(),
                source.getPrice(),
                source.getImageUrl()
        );

        return product;
    }

    @Override
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id + "에 해당하는 장난감을 찾지 못해 삭제할 수 없습니다."));

        productRepository.delete(product);
    }
}
