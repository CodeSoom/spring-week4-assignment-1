package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductDto;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.math.BigDecimal;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 상품 id

    private String name; // 상품 이름

    private String maker; // 상품 제작자

    private BigDecimal price; // 상품 가격

    private String imageUrl; // 상품 이미지 주소

    public Product(ProductBuilder productBuilder) {
        this.id = productBuilder.id;
        this.name = productBuilder.name;
        this.maker = productBuilder.maker;
        this.price = productBuilder.price;
        this.imageUrl = productBuilder.imageUrl;
    }

    public Product() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static class ProductBuilder {
        private Long id;
        private String name;
        private String maker;
        private BigDecimal price;
        private String imageUrl;

        public ProductBuilder id(Long id) {
            this.id = id;
            return this;
        }
        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder maker(String maker) {
            this.maker = maker;
            return this;
        }

        public ProductBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ProductBuilder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }

    /**
     * ProductDto의 각 필드 값이 존재한다면 ProductDto의 값으로 Product의 필드를 수정한다.
     * */
    public void modify(ProductDto productDto) {
        if(!StringUtils.isEmpty(productDto.getName())) { this.name = productDto.getName();}
        if(!StringUtils.isEmpty(productDto.getMaker())) { this.maker = productDto.getMaker();}
        if(productDto.getPrice() != null) { this.price = productDto.getPrice();}
        if(!StringUtils.isEmpty(productDto.getImageUrl())) { this.imageUrl = productDto.getImageUrl();}
    }
}
