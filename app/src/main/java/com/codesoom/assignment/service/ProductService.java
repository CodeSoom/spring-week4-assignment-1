package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * 모든 장난감 리스트를 반환합니다.
     *
     * @return
     */
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * id에 해당하는 장난감을 반환합니다.
     *
     * @param id
     * @return id에 해당하는 Product
     */
    public Product getProduct(Long id) {
        return productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * 장난감을 저장합니다.
     *
     * @param productDto
     * @return 추가된 Product
     */
    public Product addProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        return productRepository.save(product);
    }

    /**
     * id에 해당하는 장난감을 수정합니다.
     * id에 해당하는 장난감이 없으면 ProductNotFoundException을 던집니다.
     *
     * @param id
     * @param productDto
     * @return
     */
    public Product updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException(id));
        modelMapper.map(productDto, product);
        return productRepository.save(product);
    }

    /**
     * id에 해당 하는 장난감을 수정합니다.
     * id에 해당하는 장난감이 없으면 ProductNotFoundException을 던집니다.
     *
     * @param id
     */
    public void deleteTask(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}
