package com.codesoom.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public String getGreeting() {
        return "안녕하세요! 잘 부탁드립니다.";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
