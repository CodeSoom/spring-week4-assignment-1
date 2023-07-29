package com.codesoom.assignment.product.application;

import com.codesoom.assignment.product.infra.persistence.ProductRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaTest {
    @Autowired
    ProductRepository productRepository;

    public ProductRepository getProductRepository() {
        return productRepository;
    }
}
