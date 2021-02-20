package com.codesoom.assignment.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CatToy 클래스")
class CatToyTest {
    private final long givenID = 1L;
    private final String givenName = "cat nip";
    private final String givenBrand = "my home";
    private final double givenPrice = 1000;
    private final String givenImageURL = "http://toy.cat/cat-nip.png";
    private final Toy givenCatToy = new CatToy(
            givenID, givenName, givenBrand, givenPrice, givenImageURL
    );
    private final String modifiedBrand = "cat company";

    @Test
    @DisplayName("id 메소드는 id를 리턴한다.")
    void id() {
        assertThat(givenCatToy.id()).isEqualTo(givenID);
    }

    @Test
    @DisplayName("name 메소드는 name 을 리턴한다.")
    void name() {
        assertThat(givenCatToy.name()).isEqualTo(givenName);
    }

    @Test
    @DisplayName("brand 메소드는 brand 를 리턴한다.")
    void brand() {
        assertThat(givenCatToy.brand()).isEqualTo(givenBrand);
    }

    @Test
    @DisplayName("price 메소드는 price 를 리턴한다.")
    void price() {
        assertThat(givenCatToy.price()).isEqualTo(givenPrice);
    }

    @Test
    @DisplayName("imageURL 메소드는 imageURL 을 리턴한다.")
    void imageURL() {
        assertThat(givenCatToy.imageURL()).isEqualTo(givenImageURL);
    }

    @Test
    @DisplayName("modify 메소드는 멤버변수의 값을 변경한다.")
    void modify() {
        givenCatToy.modify(
                givenName, modifiedBrand, givenPrice, givenImageURL
        );

        assertThat(givenCatToy.brand()).isEqualTo(modifiedBrand);
    }
}
