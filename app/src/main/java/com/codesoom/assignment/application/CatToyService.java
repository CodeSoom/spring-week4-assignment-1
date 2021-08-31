package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.CatToyRepository;
import com.codesoom.assignment.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatToyService {

    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<Product> getProducts() {
        return catToyRepository.findAll();
    }

    public Product getProduct(Long id) {
        return catToyRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product saveProduct(Product product) {
        return catToyRepository.save(product);
    }

    public Product updateProduct(Product source, Long id) {
        Product product = catToyRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));

        updateProduct(source, product);

        return product;
    }

    public void deleteProduct(Product product){
        catToyRepository.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException(product.getId()));

        catToyRepository.delete(product);
    }

    private void updateProduct(Product source, Product product) {
        product.setName(source.getName());
        product.setMaker(source.getMaker());
        product.setPrice(source.getPrice());
        product.setImageUrl(source.getImageUrl());
    }

}
