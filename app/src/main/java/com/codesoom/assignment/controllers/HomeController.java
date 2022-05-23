package com.codesoom.assignment.controllers;

import com.codesoom.assignment.interfaces.Shop;
import com.codesoom.assignment.domain.ToyShop;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final Shop shop;

    public HomeController() {
        shop = new ToyShop("고양이 장난감 가게");
    }

    @GetMapping("/")
    public String home() {
        return shop.name();
    }
}
