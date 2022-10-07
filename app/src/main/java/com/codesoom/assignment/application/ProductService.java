package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product createProduct(Product requestProduct) {
        return productRepository.save(requestProduct);
    }

    public Product updateProduct(Long id, Product requestProduct) {
        Product product = getProduct(id);

        String name = requestProduct.getName();
        Integer price = requestProduct.getPrice();
        String maker = requestProduct.getMaker();
        String imageUrl = requestProduct.getImageUrl();

        if (name != null) {
            product.changeNameTo(name);
        }
        if (price != null) {
            product.changePriceTo(price);
        }
        if (maker != null) {
            product.changeMakerTo(maker);
        }
        if (imageUrl != null) {
            product.changeImageUrlTo(imageUrl);
        }

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        getProduct(id);
        productRepository.deleteById(id);
    }
}
