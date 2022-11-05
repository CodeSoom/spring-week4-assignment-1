package com.codesoom.assignment.product.adapter.out.persistence;

import com.codesoom.assignment.product.application.port.out.CommandProductPort;
import com.codesoom.assignment.product.application.port.out.QueryProductPort;
import com.codesoom.assignment.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPersistenceAdapter
        extends QueryProductPort, CommandProductPort, JpaRepository<Product, Long> {
}
