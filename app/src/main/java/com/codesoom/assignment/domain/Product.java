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
//        if(id == null) throw new InvalidParamException("Product 생성자 id가 Null입니다.");
        if(StringUtils.isEmpty(name)) throw new InvalidParamException("Product 생성자 name이 empty입니다.");
        if(StringUtils.isEmpty(maker)) throw new InvalidParamException("Product 생성자 maker가 empty입니다.");
        if(price == null) throw new InvalidParamException("Product 생성자 price가 Null입니다.");
        if(StringUtils.isEmpty(imageUrl)) throw new InvalidParamException("Product 생성자 imageUrl이 empty입니다.");

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
