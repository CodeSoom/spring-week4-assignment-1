package com.codesoom.assignment.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import lombok.extern.jackson.Jacksonized;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Accessors(fluent = true)
@AllArgsConstructor
@Getter
@Jacksonized
@Builder(builderClassName = "builder", builderMethodName = "")
public class CatToyDTO {
    @JsonProperty("name")
    String name;

    @JsonProperty("maker")
    String maker;

    @JsonProperty("price")
    Double price;

    @JsonProperty("imageUrl")
    String imageUrl;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CatToyDTO) {
            CatToyDTO dto = (CatToyDTO) obj;
            return this.name().equals(dto.name())
                    && this.maker().equals(dto.maker())
                    && this.price().equals(dto.price())
                    && this.imageUrl().equals(dto.imageUrl());
        }
        return false;
    }
}
