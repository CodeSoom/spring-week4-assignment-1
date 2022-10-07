package com.codesoom.assignment.application.command;

import com.codesoom.assignment.domain.ProductCommand;
import com.codesoom.assignment.domain.ProductInfo;

public interface ProductCommandService {

    ProductInfo createProduct(ProductCommand.Register command);

    ProductInfo updateProduct(ProductCommand.UpdateReq command);

    void deleteProduct(Long id);
}

