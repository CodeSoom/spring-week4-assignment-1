package com.codesoom.assignment.controllers;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
* TODO
*  1. products() 전체 장난감 목록
*  2. product(id) 하나의 장난감
*  3. create(toy) 장난감 등록
*  4. delete(id) 등록된 장난감 삭제
* */
class ToyControllerTest {
    @Test
    void products() {
        ToyController toyController = new ToyController();

        assertThat(toyController.products()).isEmpty();
    }
}