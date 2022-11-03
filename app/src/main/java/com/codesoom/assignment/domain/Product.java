package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Categorization> categorizationList = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getCategoryNameList() {
        return categorizationList.stream()
                .map(categorization -> categorization.getCategory().getName())
                .collect(Collectors.toUnmodifiableList());
    }

    public void addProductCategory(Categorization categorization) {
        categorizationList.add(categorization);
    }

    public void update(ProductDto src) {
        if (src.getName() != null) {
            this.name = src.getName();
        }

        if (src.getMaker() != null) {
            this.maker = src.getMaker();
        }

        if (src.getPrice() != null) {
            this.price = src.getPrice();
        }

        if (src.getImageUrl() != null) {
            this.imageUrl = src.getImageUrl();
        }

        final List<String> categoryNameList = src.getCategoryNameList();
        if (categoryNameList != null && !categoryNameList.isEmpty()) {
            this.
        }
    }

}
