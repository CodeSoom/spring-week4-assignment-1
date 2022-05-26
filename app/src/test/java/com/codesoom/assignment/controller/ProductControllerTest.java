package com.codesoom.assignment.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProductController Test")
class ProductControllerTest {

    @Nested
    @DisplayName("GET /products")
    class list {

        @Nested
        @DisplayName("고양이 장난감 목록을 조회하면")
        class when_list {

            @Test
            @DisplayName("200 OK를 응답합니다..")
            void httpStatus_200() {

            }
        }
    }

    @Nested
    @DisplayName("GET /products/{id}")
    class detail {

        @Nested
        @DisplayName("id로 고양이 장난감 목록을 조회하면")
        class when_list_with_id {

            @Test
            @DisplayName("200 OK를 응답합니다.")
            void httpStatus_200() {

            }
        }
    }

    @Nested
    @DisplayName("POST /products")
    class regist {

        @Nested
        @DisplayName("고양이 장난감을 등록하면")
        class when_list_with_id {

            @Test
            @DisplayName("201 CREATED를 응답합니다.")
            void httpStatus_201() {

            }
        }
    }

    @Nested
    @DisplayName("PATCH /products/{id}")
    class modify {

        @Nested
        @DisplayName("고양이 장난감을 수정하면")
        class when_modify_with_id {

            @Test
            @DisplayName("200 CREATED를 응답합니다.")
            void httpStatus_200() {

            }
        }
    }

    @Nested
    @DisplayName("DELETE /products/{id}")
    class delete {

        @Nested
        @DisplayName("고양이 장난감을 삭제하면")
        class when_delete_with_id {

            @Test
            @DisplayName("204 NO CONTENT를 응답합니다.")
            void httpStatus_204() {

            }
        }
    }

}