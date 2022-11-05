package com.codesoom.assignment.domain;

import com.codesoom.assignment.dto.ProductDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Product {

    @Id @GeneratedValue
    @Column
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private final List<Categorization> categorizations = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String maker, Integer price, String imageUrl, List<Category> categories) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
        this.addCategories(categories);
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

    public static Product of(ProductDto dto, List<Category> categories) {
        return new Product(
                dto.getName(),
                dto.getMaker(),
                dto.getPrice(),
                dto.getImageUrl(),
                categories);
    }

    public List<String> getCategoryNames() {
        return categorizations.stream()
                .map(categorization -> categorization.getCategory().getName())
                .collect(Collectors.toUnmodifiableList());
    }

    public void addCategories(List<Category> categories) {
        for (Category category : categories) {
            addCategorization(new Categorization(this, category));
        }
    }

    public void addCategorization(Categorization categorization) {
        categorizations.add(categorization);
        categorization.getCategory().addCategorization(categorization);
    }

    private void deleteAllCategories() {
        for (Categorization categorization : categorizations) {
            categorization.getCategory()
                    .deleteCategorization(categorization);
        }

        categorizations.clear();
    }

    public void updateCategories(List<Category> categories) {
        deleteAllCategories();
        addCategories(categories);
    }

    public void updateInfo(ProductDto src, List<Category> categories) {
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

        if (categories != null) {
            updateCategories(categories);
        }
    }

}
