package com.codesoom.assignment.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatToy 클래스")
public class CatToyTest {
    private static final String CAT_TOY_NAME = "test_cat_toy";
    private static final String CAT_TOY_MAKER = "test_maker";
    private static final String CAT_TOY_IMAGE = "http://test.jpg";
    private static final Integer CAT_TOY_PRICE = 10000;

    private CatToy catToy;


    @DisplayName("getName 메서드는")
    @Nested
    class Describe_getName {
        @DisplayName("이름이 등록되었다면")
        @Nested
        class Context_with_name {
            String subject() {
                return catToy.getName();
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
                catToy.setName(CAT_TOY_NAME);
            }

            @DisplayName("이름(String)을 리턴한다.")
            @Test
            void it_returns_name() {
                assertThat(subject()).isEqualTo(CAT_TOY_NAME);
            }
        }
    }

    @DisplayName("setName 메서드는")
    @Nested
    class Describe_setName {
        @DisplayName("이름(String)이 주어지면")
        @Nested
        class Context_with_name {
            void subject() {
                catToy.setName(CAT_TOY_NAME);
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
            }

            @DisplayName("이름 필드에 그 이름(String)을 저장한다. ")
            @Test
            void it_returns_name() {
                subject();
                assertThat(catToy.getName()).isEqualTo(CAT_TOY_NAME);
            }
        }
    }

    @DisplayName("getMaker 메서드는")
    @Nested
    class Describe_getMaker {
        @DisplayName("메이커가 등록되었다면")
        @Nested
        class Context_with_maker {
            String subject() {
                return catToy.getMaker();
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
                catToy.setMaker(CAT_TOY_MAKER);
            }

            @DisplayName("메이커(String)를 리턴한다.")
            @Test
            void it_returns_maker() {
                assertThat(subject()).isEqualTo(CAT_TOY_MAKER);
            }
        }
    }

    @DisplayName("setMaker 메서드는")
    @Nested
    class Describe_setMaker {
        @DisplayName("메이커(String)가 주어지면")
        @Nested
        class Context_with_maker {
            void subject() {
                catToy.setMaker(CAT_TOY_MAKER);
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
            }

            @DisplayName("메이커 필드에 그 메이커(String)를 저장한다. ")
            @Test
            void it_returns_maker() {
                subject();
                assertThat(catToy.getMaker()).isEqualTo(CAT_TOY_MAKER);
            }
        }
    }

    @DisplayName("getPrice 메서드는")
    @Nested
    class Describe_getPrice {
        @DisplayName("가격이 등록되었다면")
        @Nested
        class Context_with_price {
            Integer subject() {
                return catToy.getPrice();
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
                catToy.setPrice(CAT_TOY_PRICE);
            }

            @DisplayName("가격(Integer)을 리턴한다.")
            @Test
            void it_returns_price() {
                assertThat(subject()).isEqualTo(CAT_TOY_PRICE);
            }
        }
    }

    @DisplayName("setPrice 메서드는")
    @Nested
    class Describe_setPrice {
        @DisplayName("가격(Integer)이 주어지면")
        @Nested
        class Context_with_price {
            void subject() {
                catToy.setPrice(CAT_TOY_PRICE);
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
            }

            @DisplayName("가격 필드에 그 가격(Integer)을 저장한다. ")
            @Test
            void it_returns_price() {
                subject();
                assertThat(catToy.getPrice()).isEqualTo(CAT_TOY_PRICE);
            }
        }
    }

    @DisplayName("getImage 메서드는")
    @Nested
    class Describe_getImage {
        @DisplayName("이미지가 등록되었다면")
        @Nested
        class Context_with_image {
            String subject() {
                return catToy.getImage();
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
                catToy.setImage(CAT_TOY_IMAGE);
            }

            @DisplayName("이미지 URL(String)를 리턴한다.")
            @Test
            void it_returns_image() {
                assertThat(subject()).isEqualTo(CAT_TOY_IMAGE);
            }
        }
    }

    @DisplayName("setImage 메서드는")
    @Nested
    class Describe_setImage {
        @DisplayName("이미지 URL(String)이 주어지면")
        @Nested
        class Context_with_image {
            void subject() {
                catToy.setImage(CAT_TOY_IMAGE);
            }

            @BeforeEach
            void prepare() {
                catToy = new CatToy();
            }

            @DisplayName("이미지 필드에 그 이미지URL(String)을 저장한다.")
            @Test
            void it_returns_image() {
                subject();
                assertThat(catToy.getImage()).isEqualTo(CAT_TOY_IMAGE);
            }
        }
    }
}
