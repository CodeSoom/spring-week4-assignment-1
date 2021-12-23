package com.codesoom.assignment.controllers;

import com.codesoom.assignment.domain.Toy;
import org.junit.jupiter.api.BeforeEach;
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
    private ToyController toyController;
    private static final String TOY_NAME = "test 장난감";
    private static final String CREATE_PREFIX = "new";
    private static final String TOY_MAKER = "애옹이네 장난감 가게";
    private static final Integer TOY_PRICE = 5000;
    private static final String TOY_IMAGE = "someUrl";


    @BeforeEach
    void setUp() {
        Toy toy = new Toy();
        toy.setName(TOY_NAME);
        toyController = new ToyController();

        toyController.create(toy);
    }

    @Test
    void products() {
        assertThat(toyController.products()).isNotEmpty();
    }

    @Test
    void create() {
        Toy newToy = new Toy();

        newToy.setName(CREATE_PREFIX + TOY_NAME);
        newToy.setMaker(TOY_MAKER);
        newToy.setPrice(TOY_PRICE);
        newToy.setImage(TOY_IMAGE);

        Toy createdToy = toyController.create(newToy);

        assertThat(createdToy.getName()).isEqualTo(CREATE_PREFIX + TOY_NAME);
        assertThat(createdToy.getMaker()).isEqualTo(TOY_MAKER);
        assertThat(createdToy.getPrice()).isEqualTo(TOY_PRICE);
        assertThat(createdToy.getImage()).isEqualTo(TOY_IMAGE);
    }

    @Test
    void product() {
        Toy find = toyController.product(1L);

        assertThat(find.getName()).isEqualTo(TOY_NAME);
    }

    @Test
    void delete() {
        int oldSize = toyController.products().size();

        toyController.delete(1L);

        int newSize = toyController.products().size();

        assertThat(newSize - oldSize).isEqualTo(-1);
    }
}