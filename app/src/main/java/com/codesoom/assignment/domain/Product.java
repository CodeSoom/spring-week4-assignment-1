package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 상품 엔티티.
 */
@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")   //Equals()와 Hashcode()를 생성할때 명시적으로 id를 포함시켜 동등성 비교를 id로 한다.
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 상품명.
     */
    private String name;

    /**
     * 상품의 브랜드.
     */
    private String maker;

    private Integer price;

    @Lob
    private String imageUrl;

    @Builder
    public Product(Long id, String name, String maker, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    /**
     * 상품을 수하고 수정된 상품을 반환합니다.
     *
     * @param productDto 수정할 상품의 정보
     * @return 수정된 product
     */
    public Product update(ProductDto productDto) {
        this.name = productDto.getName();
        this.maker = productDto.getMaker();
        this.price = productDto.getPrice();
        this.imageUrl = productDto.getImageUrl();
        return this;
    }
}
