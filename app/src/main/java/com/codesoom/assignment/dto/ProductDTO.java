package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class ProductDTO {

    private final String name;
    private final String maker;
    private final int price;
    private final String fileName;

    @JsonCreator
    public ProductDTO(@JsonProperty("name") String name,
                      @JsonProperty("maker") String maker,
                      @JsonProperty("price")int price,
                      @JsonProperty("fileName") String fileName) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.fileName = fileName;
    }

    public Product toProduct(){
        return new Product(null , this.name , this.maker , this.price , this.fileName);
    }
}
