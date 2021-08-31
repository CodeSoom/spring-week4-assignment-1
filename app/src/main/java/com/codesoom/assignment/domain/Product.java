package com.codesoom.assignment.domain;

public class Product {
        private Long id;
        private String name;
        private String maker;
        private Long price;
        private String imageUrl;

        public  Product(Long id, String name, String maker,
                        Long price, String imageUrl) {

            this.id = id;
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

        public Long getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }
}
