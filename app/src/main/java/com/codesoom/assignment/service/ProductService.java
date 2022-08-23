package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductJPARepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService{

    private final ProductJPARepository repository;

    public ProductService(ProductJPARepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll(){
        return (List<Product>) repository.findAll();
    }

    public Optional<Product> findById(Long id){
        // TODO
        // Product 못 찾았을 때 커스텀 예외 추가
        return repository.findById(id);
    }

    public Product save(Product product){
        return repository.save(product);
    }

    public Product update(Long id , Product product){
        // TODO
        // 수정 정보 적용
        Optional<Product> findProduct = repository.findById(id);
        return product;
    }

    public void deleteById(Long id){
        repository.deleteById(id);
    }
}
