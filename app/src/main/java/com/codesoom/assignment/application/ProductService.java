package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Collection<Product> getProducts(){
        return productRepository.findAll();
    }

    public Product getToys(Long id){
        return productRepository.findById(id)
                .orElseThrow(() ->new ProductNotFoundException(id));
    }

    public Product createToy(Product source){

        return productRepository.save(source);
    }

    /**
     * TODO
     * - 특정 항목만 변경해도 업데이트가 되어야한다.
     * - 빈 항목이 있다면 업데이트 하지 않고 그대로 유지
     * - 항목이 알맞은 형식일 때만 업데이트해야한다. (price - long)
     *
     */
    public Product updateToy(Long id, Product source){

        Product toy = getToys(id);

        toy.updateProduct(source);
        return productRepository.save(toy);
    }

    public Product deleteToy(Long id){
        Product toy = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(toy);
        return toy;
    }

}
