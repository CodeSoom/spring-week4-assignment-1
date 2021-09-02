package com.codesoom.assignment.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 상품.
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@DynamicUpdate
@Entity
public class Product {

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Exclude
    private Long id;

    private String name;
    private String maker;
    private Long price;
    private String imageUrl;

    /**
     * 수정될 정보가 담긴 상품을 받아, 현재 상품 정보를 수정하고 리턴합니다.
     *
     * @param source 수정될 정보가 담긴 상품
     * @return 정보가 수정된 현재 상품
     */
    public Product updateInfo(Product source) {
        this.name = source.name;
        this.maker = source.maker;
        this.price = source.price;
        this.imageUrl = source.imageUrl;

        return this;
    }
}
