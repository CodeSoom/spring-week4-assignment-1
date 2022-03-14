package com.codesoom.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
* TODO: 고양이 장난감 가게 서버 만들기
*  1. 장난감 domain 테스트 및 구현 (완료)
*  2. 장난감 도메인 controller 만들기
*   2-1. 장난감 도메인 controller의 unit test 를 만들면서 만들기
*   2-2. 장난감 도메인 controller의 web test를 만들면서 만들기
*  3. controller에서 service layer 로 관심사 분리하기
*   3-1. 분리하면서 unit test 만들기
*  4. 장난감 레포지토리 만들기
*  5. spring data JAP 를 사용하기
* */

@SpringBootApplication
public class App {
    public String getGreeting() {
        return "Hello, world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
