package com.codesoom.assignment.domain;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.dto.CatToyModel;

import java.util.List;
import java.util.stream.Collectors;

public class CatToyViewModel {
    private String name;
    private String maker;
    private Integer price;
    private String imageUrl;

    public CatToyViewModel() {
    }

    public CatToyViewModel(String name, String maker, Integer price, String imageUrl) {
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public static class Request extends CatToyViewModel {
        public Request() {
        }

        public Request(String name, String maker, int price, String imageUrl) {
            super(name, maker, price, imageUrl);
        }
    }

    public static class Response extends CatToyViewModel {
        private Long id;

        public Response() {
        }

        public Response(CatToyModel catToy) {
            this (catToy.name(), catToy.maker(), catToy.price(), catToy.imageUrl(), catToy.id());
        }

        public Response(String name, String maker, int price, String imageUrl, Long id) {
            super(name, maker, price, imageUrl);
            this.id = id;
        }

        public static List<Response> ofList(List<CatToyModel> catToys) {
            return catToys.stream()
                    .map(Response::new)
                    .collect(Collectors.toList());
        }


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
