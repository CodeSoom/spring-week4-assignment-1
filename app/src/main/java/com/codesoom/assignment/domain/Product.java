package com.codesoom.assignment.domain;

import com.codesoom.assignment.common.exception.InvalidParamException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@lombok.Generated
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String maker;

    private Long price;

    private String imageUrl;

    @Builder
    public Product(
            Long id,
            String name,
            String maker,
            Long price,
            String imageUrl
    ) {
        if(StringUtils.isEmpty(name)) throw new InvalidParamException("이름이 비어있습니다.");
        if(StringUtils.isEmpty(maker)) throw new InvalidParamException("제조사가 비어있습니다.");
        if(price == null) throw new InvalidParamException("가격이 비어있습니다.");

        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product modifyProduct(Product product) {
        this.name = product.getName();
        this.maker = product.getMaker();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();

        return this;
    }
}
