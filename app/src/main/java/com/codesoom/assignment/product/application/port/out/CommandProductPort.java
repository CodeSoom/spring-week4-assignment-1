package com.codesoom.assignment.product.application.port.out;

import com.codesoom.assignment.product.domain.Product;

public interface CommandProductPort {
    Product save(Product product);

    void delete(Product product);

    void deleteAllInBatch();
}
