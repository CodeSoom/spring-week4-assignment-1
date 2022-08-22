package com.codesoom.assignment;


import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;

public abstract class BaseProductTest {

    protected static final Long PRODUCT_ID = 1L;
    protected static final String PRODUCT_NAME = "sample cat";

    protected static final Long PRODUCT_PRICE = 300L;
    protected static final String IMAGE_URL = "test url";
    protected static final String MAKER_NAME = "sample maker";
    protected static final String ERROR_MSG = "Product not found";

    protected Product supplyDummyProduct() {

        ProductDto productDto = new ProductDto(PRODUCT_NAME, MAKER_NAME, PRODUCT_PRICE, IMAGE_URL);

        return new Product(productDto);
    }

    protected String supplyErrorMSG(Long id){
        return ERROR_MSG + ": " + id;
    }
}
