package com.codesoom.assignment.applications;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        Product newProduct = Product.of(
                product.getName(),
                product.getMaker(),
                product.getPrice(),
                product.getImageUrl());
        return productRepository.save(newProduct);
    }

    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException(id));
    }

    public Product update(Long id, Product product) {
        Product updatedProduct = findById(id);

        updatedProduct.setName(product.getName());
        updatedProduct.setMaker(product.getMaker());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setImageUrl(product.getImageUrl());

        return productRepository.save(updatedProduct);
    }

    public void deleteById(Long id) {
        try {
            productRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException(id);
        }
    }


}
