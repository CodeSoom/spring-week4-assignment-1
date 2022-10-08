package com.codesoom.assignment.application;

import com.codesoom.assignment.ProductNotFoundException;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DatabaseCommandProductService implements CommandProductService {

    private final ProductRepository productRepository;

    public DatabaseCommandProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product requestProduct) {
        return productRepository.save(requestProduct);
    }

    @Override
    public Product updateProduct(Long id, Product requestProduct) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

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

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        productRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();
    }
}
