package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
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

    @Test
    void create() {
        Toy toy = new Toy();
        toy.setName("test 장난감");
        ToyController toyController = new ToyController();

        int oldSize = toyController.products().size();

        toyController.create(toy);

        int newSize = toyController.products().size();

        assertThat(newSize - oldSize).isEqualTo(1);
    }

    @Test
    void product() {
        Toy toy = new Toy();
        toy.setName("test 장난감");
        ToyController toyController = new ToyController();
        toyController.create(toy);

        Toy find = toyController.product(1L);

        assertThat(find.getName()).isEqualTo("test 장난감");
    }

    @Test
    void delete() {
        Toy toy = new Toy();
        toy.setName("test 장난감");
        ToyController toyController = new ToyController();
        toyController.create(toy);

        toyController.delete(1L);

        assertThat(toyController.products()).isEmpty();
    }
}