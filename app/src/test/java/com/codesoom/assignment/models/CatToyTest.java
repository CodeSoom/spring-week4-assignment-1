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

    @Nested
    @DisplayName("equals 메소드는")
    class Describe_equals {
        @Nested
        @DisplayName("비교 대상과 모든 값이 같을 때")
        class Context_when_equals_all_properties {
            private final long compareID = givenID;
            private final String compareName = givenName;
            private final String compareBrand = givenBrand;
            private final double comparePrice = givenPrice;
            private final String compareImageURL = givenImageURL;
            private final Toy compareCatToy = new CatToy(
                    compareID, compareName, compareBrand, comparePrice, compareImageURL
            );

            @Test
            @DisplayName("true 를 리턴한다.")
            void It_returns_true() {
                assertThat(givenCatToy).isEqualTo(compareCatToy);
            }
        }

        @Nested
        @DisplayName("id 가 같지 않을 때")
        class Context_when_id_is_not_equal {
            private final long compareID = 2;
            private final String compareName = givenName;
            private final String compareBrand = givenBrand;
            private final double comparePrice = givenPrice;
            private final String compareImageURL = givenImageURL;
            private final Toy compareCatToy = new CatToy(
                    compareID, compareName, compareBrand, comparePrice, compareImageURL
            );

            @Test
            @DisplayName("false 를 리턴한다.")
            void It_returns_false() {
                assertThat(givenCatToy).isNotEqualTo(compareCatToy);
            }
        }

        @Nested
        @DisplayName("name 이 같지 않을 때")
        class Context_when_name_is_not_equal {
            private final long compareID = givenID;
            private final String compareName = "scratcher";
            private final String compareBrand = givenBrand;
            private final double comparePrice = givenPrice;
            private final String compareImageURL = givenImageURL;
            private final Toy compareCatToy = new CatToy(
                    compareID, compareName, compareBrand, comparePrice, compareImageURL
            );

            @Test
            @DisplayName("false 를 리턴한다.")
            void It_returns_false() {
                assertThat(givenCatToy).isNotEqualTo(compareCatToy);
            }
        }

        @Nested
        @DisplayName("brand 가 같지 않을 때")
        class Context_when_brand_is_not_equal {
            private final long compareID = givenID;
            private final String compareName = givenName;
            private final String compareBrand = "your home";
            private final double comparePrice = givenPrice;
            private final String compareImageURL = givenImageURL;
            private final Toy compareCatToy = new CatToy(
                    compareID, compareName, compareBrand, comparePrice, compareImageURL
            );

            @Test
            @DisplayName("false 를 리턴한다.")
            void It_returns_false() {
                assertThat(givenCatToy).isNotEqualTo(compareCatToy);
            }
        }

        @Nested
        @DisplayName("price 가 같지 않을 때")
        class Context_when_price_is_not_equal {
            private final long compareID = givenID;
            private final String compareName = givenName;
            private final String compareBrand = givenBrand;
            private final double comparePrice = 2000;
            private final String compareImageURL = givenImageURL;
            private final Toy compareCatToy = new CatToy(
                    compareID, compareName, compareBrand, comparePrice, compareImageURL
            );

            @Test
            @DisplayName("false 를 리턴한다.")
            void It_returns_false() {
                assertThat(givenCatToy).isNotEqualTo(compareCatToy);
            }
        }

        @Nested
        @DisplayName("imageURL 가 같지 않을 때")
        class Context_when_imageURL_is_not_equal {
            private final long compareID = givenID;
            private final String compareName = givenName;
            private final String compareBrand = givenBrand;
            private final double comparePrice = givenPrice;
            private final String compareImageURL = "http://toy.cat/scratcher.png";;
            private final Toy compareCatToy = new CatToy(
                    compareID, compareName, compareBrand, comparePrice, compareImageURL
            );

            @Test
            @DisplayName("false 를 리턴한다.")
            void It_returns_false() {
                assertThat(givenCatToy).isNotEqualTo(compareCatToy);
            }
        }

        @Nested
        @DisplayName("CatToy 가 아닐 때")
        class Context_when_type_is_not_CatToy {
            @Test
            @DisplayName("false 를 리턴한다.")
            void It_returns_false() {
                assertThat(givenCatToy).isNotEqualTo("toy");
            }
        }
    }
}
