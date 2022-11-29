package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return repository.findById(id)
                .stream()
                .findFirst();
    }

//    @Override
//    public Product update(Long id, Product toy) {
//        Optional<Product> Product = repository.findById(id);
//
//        repository.save(toy);
//        return null;
//    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}
