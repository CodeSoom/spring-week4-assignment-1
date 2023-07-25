package com.codesoom.assignment.adapter.out.persistence;

import com.codesoom.assignment.adapter.in.web.ProductCommand;
import com.codesoom.assignment.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public static Product toDomain(ProductEntity productEntity) {
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .maker(productEntity.getMaker())
                .price(productEntity.getPrice())
                .imageUrl(productEntity.getImageUrl())
                .build();
    }

    public static ProductEntity toEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId())
                .name(product.getName())
                .maker(product.getMaker())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .build();
    }

    public static Product commandToDomain(ProductCommand productCommand) {
        return Product.builder()
                .name(productCommand.getName())
                .maker(productCommand.getMaker())
                .price(productCommand.getPrice())
                .imageUrl(productCommand.getImageUrl())
                .build();
    }

}
