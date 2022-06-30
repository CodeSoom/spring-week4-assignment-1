package com.codesoom.assignment;

public class Product {
    private Long id;
    private final Category category;
    private final String name;
    private final String maker;
    private final int price;
    private final String imageUrl;

    public Product(Long id, Category category, String name, String maker, int price, String imageUrl) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(Category category, String name, String maker, int price, String imageUrl) {
        this.category = category;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Product)) {
            return false;
        }

        Product t = (Product) o;

        return category.equals(t.category)
                && name.equals(t.name)
                && maker.equals(t.maker)
                && price == t.price
                && imageUrl.equals(t.imageUrl);
    }
}
