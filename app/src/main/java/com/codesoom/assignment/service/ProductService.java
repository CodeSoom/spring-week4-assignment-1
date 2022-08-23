package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ResourceNotFoundException;
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

    public Product findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("상품이 존재하지 않습니다. id : " + id));
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
