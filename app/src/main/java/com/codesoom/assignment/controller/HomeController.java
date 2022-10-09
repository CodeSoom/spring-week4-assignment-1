package com.codesoom.assignment.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @GetMapping("/healthcheck")
    public String checkServerHealth() {
        return "Server is OK";
    }
}
