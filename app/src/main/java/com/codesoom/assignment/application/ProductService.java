package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository repository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<Product> getProducts() {
        return repository.findAll();
    }

    public Product getProduct(Long id) {
        return repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return repository.save(product);
    }

    public Product removeProduct(Product product) {
        repository.delete(product);

        return product;
    }

    public Product updateProduct(Long id, ProductDto source) {
        Product product = getProduct(id);

        product.setPrice(source.getPrice());
        product.setImageUrl(source.getImageUrl());
        product.setMaker(source.getMaker());
        product.setName(source.getName());

        return product;
    }
}
