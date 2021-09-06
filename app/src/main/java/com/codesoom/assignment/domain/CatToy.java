package com.codesoom.assignment.domain;

// 이름 장난감 뱀
// 메이커 애옹이네 장난감
// 가격 5000
// 이미지 url
public class CatToy {
    private Long id;
    private String catToyName;
    private String makerName;
    private Double price;
    private String imageUrl;

    public CatToy(Long id, String catToyName, String makerName, Double price, String imageUrl) {
        this.id = id;
        this.catToyName = catToyName;
        this.makerName = makerName;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CatToy(String catToyName) {
        this.catToyName = catToyName;
    }

    public String getCatToyName() {
        return this.catToyName;
    }


    public void setCatToyName(String catToyName) {
        this.catToyName = catToyName;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
