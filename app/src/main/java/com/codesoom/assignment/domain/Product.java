package com.codesoom.assignment.domain;

public class Product {
    private ProductId id;

    private String name;
    private String maker;
    private String price;
    private String imageURL;

    public Product(ProductId id, String name, String maker, String price, String imageURL) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageURL = imageURL;
    }

    public ProductId productId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product)) {
            return false;
        }
        Product product = (Product) o;
        return id.equals(product.id);
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public String getPrice() {
        return price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void updateInformation(String newName, String newMaker, String newPrice, String newImageURL) {
        this.name = newName;
        this.maker = newMaker;
        this.price = newPrice;
        this.imageURL = newImageURL;
    }
}
