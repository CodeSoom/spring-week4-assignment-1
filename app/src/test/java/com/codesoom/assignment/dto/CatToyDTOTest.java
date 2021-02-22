package com.codesoom.assignment.dto;

import com.codesoom.assignment.models.CatToy;
import com.codesoom.assignment.models.Toy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToyDTO 클래스")
class CatToyDTOTest {
    private final String givenName = "cat nip";
    private final String givenMaker = "dog";
    private final double givenPrice = 1000;
    private final String givenImageUrl = "https://cat.toy/cat-nip.png";
    private final CatToyDTO givenCatToyDTO = new CatToyDTO(
            givenName, givenMaker, givenPrice, givenImageUrl
    );
    private final String givenCatToyDTOJson = String.format(
            "{\"name\":\"%s\",\"maker\":\"%s\",\"price\":%.1f,\"imageUrl\":\"%s\"}",
            givenName, givenMaker, givenPrice, givenImageUrl
    );

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void name() {
        assertThat(givenCatToyDTO.name()).isEqualTo(givenName);
    }

    @Test
    void maker() {
        assertThat(givenCatToyDTO.maker()).isEqualTo(givenMaker);
    }

    @Test
    void price() {
        assertThat(givenCatToyDTO.price()).isEqualTo(givenPrice);
    }

    @Test
    void imageUrl() {
        assertThat(givenCatToyDTO.imageUrl()).isEqualTo(givenImageUrl);
    }

    @Test
    void stringToCatToyDTO() throws JsonProcessingException {
        CatToyDTO catToyDTO = objectMapper.readValue(givenCatToyDTOJson, CatToyDTO.class);

        assertThat(catToyDTO.name()).isEqualTo(givenCatToyDTO.name());
        assertThat(catToyDTO.maker()).isEqualTo(givenCatToyDTO.maker());
        assertThat(catToyDTO.price()).isEqualTo(givenCatToyDTO.price());
        assertThat(catToyDTO.imageUrl()).isEqualTo(givenCatToyDTO.imageUrl());
    }

    @Test
    void constructorGivenToy() {
        Toy givenCatToy = new CatToy(0L, givenName, givenMaker, givenPrice, givenImageUrl);
        CatToyDTO catToyDTO = new CatToyDTO(givenCatToy);

        assertThat(catToyDTO.name()).isEqualTo(givenCatToy.name());
        assertThat(catToyDTO.maker()).isEqualTo(givenCatToy.brand());
        assertThat(catToyDTO.price()).isEqualTo(givenCatToy.price());
        assertThat(catToyDTO.imageUrl()).isEqualTo(givenCatToy.imageURL());
    }

    @Test
    void toEntity() {
        Toy toy = givenCatToyDTO.toEntity();

        assertThat(givenCatToyDTO.name()).isEqualTo(toy.name());
        assertThat(givenCatToyDTO.maker()).isEqualTo(toy.brand());
        assertThat(givenCatToyDTO.price()).isEqualTo(toy.price());
        assertThat(givenCatToyDTO.imageUrl()).isEqualTo(toy.imageURL());
    }
}
