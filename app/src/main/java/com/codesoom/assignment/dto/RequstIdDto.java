package com.codesoom.assignment.dto;

import com.codesoom.assignment.domain.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RequstIdDto {
    private Long id;

    @Builder
    public RequstIdDto(Long id){
        this.id = id;
    }

    public Product toEntity(){
        return Product.builder()
                .id(id)
                .build();
    }
}
