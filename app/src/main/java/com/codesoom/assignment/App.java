package com.codesoom.assignment;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 스프링 어플리케이션 실행을 담당합니다.
 */
@Generated
@SpringBootApplication
public class App {

    public String getGreeting() {
        return "Hello, world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
