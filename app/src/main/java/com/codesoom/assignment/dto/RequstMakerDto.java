package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequstMakerDto {
    private String maker;

    @Builder
    public RequstMakerDto(String maker){
        this.maker = maker;
    }

    public Product toEntity(){
        return Product.builder()
                .maker(maker)
                .build();
    }
}
