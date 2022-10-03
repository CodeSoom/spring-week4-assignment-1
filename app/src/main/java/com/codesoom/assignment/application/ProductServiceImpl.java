package com.codesoom.assignment.application;

import com.codesoom.assignment.common.exception.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;
import com.codesoom.assignment.domain.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductInfo> getProducts() {
        List<ProductInfo> result = new ArrayList<>();

        productRepository.findAll().forEach(product -> result.add(new ProductInfo(product)));

        return result;
    }

    @Override
    public ProductInfo getProduct(Long id) {
        return new ProductInfo(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("요청하신 상품이 없습니다.")));
    }

    @Override
    public ProductInfo createProduct(ProductCommand.Register command) {
        return new ProductInfo(productRepository.save(command.toEntity()));
    }

    @Override
    public ProductInfo updateProduct(ProductCommand.Register command) {
        Product product = command.toEntity();
        Product findProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("요청하신 상품이 없습니다."));

        return new ProductInfo(findProduct.modifyProduct(product));
    }

    @Override
    public void deleteProduct(Long id) {
        Product findProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("요청하신 상품이 없습니다."));

        productRepository.delete(findProduct);
    }
}
