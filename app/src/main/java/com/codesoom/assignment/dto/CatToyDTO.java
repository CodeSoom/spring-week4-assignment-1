package com.codesoom.assignment.dto;

import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@Getter
public class CatToyDTO {
    @JsonProperty("name")
    String name;

    @JsonProperty("maker")
    String maker;

    @JsonProperty("price")
    Double price;

    @JsonProperty("imageUrl")
    String imageUrl;

    @JsonCreator
    public CatToyDTO(
            @JsonProperty("name") String name,
            @JsonProperty("maker") String maker,
            @JsonProperty("price") Double price,
            @JsonProperty("imageUrl") String imageUrl
    ) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public CatToyDTO(Toy toy) {
        this.name = toy.name();
        this.maker = toy.brand();
        this.price = toy.price();
        this.imageUrl = toy.imageURL();
    }

    public Toy toEntity() {
        return new CatToy(null, this.name, this.maker, this.price, this.imageUrl);
    }
}
