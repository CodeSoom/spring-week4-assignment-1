package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.application.in.ProductUseCase;
import com.codesoom.assignment.application.out.ProductPort;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.adapter.out.persistence.ProductSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductPort productPort;

    public List<Product> getProducts() {
        return productPort.findAll();
    }

    public Product getProduct(Long id) {
        return productPort.findById(id);
    }

    public Product createProduct(Product product) {
        return productPort.save(product);
    }

    //@Transactional
    public Product updateProduct(Long id, Product source) {
        Product product = productPort.findById(id);

        if (source.getName() != null) product.setName(source.getName());
        if (source.getMaker() != null) product.setMaker(source.getMaker());
        if (source.getImageUrl() != null) product.setImageUrl(source.getImageUrl());
        if (source.getPrice() != null) product.setPrice(source.getPrice());

        //product domain 과 product entity를 나누어서 사용하니까 변경 감지가 안됨.
        return productPort.save(product);
    }

    public void deleteProduct(Long id) {
        Product product = productPort.findById(id);
        productPort.delete(product);
    }

}
