package com.codesoom.assignment.controllers;

import com.codesoom.assignment.contexts.ContextProductController;
import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.repositories.ProductRepository;
import com.codesoom.assignment.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("ProductControllerTest 의")
class ProductControllerTest extends ContextProductController {

    @Autowired
    private ProductRepository productRepository;

    private MockMvc mockMvc;
    private final Product catTower = generateCatTower();


    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new ProductController(new ProductService(productRepository)))
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        productRepository.deleteAll();
    }


    @Nested
    @DisplayName("list() 매소드는")
    class Describe_list {

        @Nested
        @DisplayName("등록된 고양이 물품이 존재하지 않는다면")
        class Context_no_exist_product {

            @Test
            @DisplayName("사이즈가 0인 빈 리스트를 반환한다.")
            void it_returns_empty_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string("[]"));
            }
        }

        @Nested
        @DisplayName("등록된 고양이 물품이 1개 이상 존재하면")
        class Context_exist_product {

            private Product existed;

            @BeforeEach
            void setUp() {
                this.existed = productRepository.save(catTower);
            }

            @Test
            @DisplayName("사이즈가 0이상이고 등록된 물품 정보가 담긴 리스트를 반환한다. ")
            void it_returns_gt0_list() throws Exception {
                mockMvc.perform(get("/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(productJsonString(existed))));
            }
        }
    }


    @Nested
    @DisplayName("detail() 매소드는")
    class Describe_detail {

        @Nested
        @DisplayName("찾고자 하는 product id 가 1이상일 때")
        class Context_id_gt1_product {

            @Nested
            @DisplayName("product id 에 맞는 물품이 존재할때")
            class Context_exist_matched_product {

                @Test
                @DisplayName("id와 일치하는 물품을 반환한다.")
                void it_returns_matched_product() {

                }
            }

            @Nested
            @DisplayName("product id 에 맞는 물품이 존재하지 않을 때")
            class Context_not_exist_matched_product {

                @Test
                @DisplayName("예외를 발생시킨다.")
                void it_throws_not_found_exception() {

                }
            }

        }

        @Nested
        @DisplayName("찾고자 하는 product id 가 1미만일 때")
        class Context_id_lt1_product {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_bad_request_exception() {

            }

        }
    }


    @Nested
    @DisplayName("add() 매소드는")
    class Describe_add {

        @Nested
        @DisplayName("추가하려는 product 의 내용에 null 이나 공백이 없을 때")
        class Context_valid_input {

            private String request;
            private String response;

            @BeforeEach
            void setUp() throws JsonProcessingException {
                this.request = productReqJsonString(generateCatTowerRequest());
                this.response = productJsonString(generateCatTowerWithId());
            }

            @Test
            @DisplayName("product 을 생성하고 생성된 product 를 반환한다.")
            void it_returns_created_product() throws Exception {
                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(response)));
            }
        }

        @Nested
        @DisplayName("추가하려는 product 의 내용에 null 이나 공백이 있을 때")
        class Context_invalid_input {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_bad_request_exception() {

            }
        }
    }


    @Nested
    @DisplayName("update() 매소드는")
    class Describe_update {

        @Nested
        @DisplayName("찾고자 하는 product id 가 1이상이고")
        class Context_id_gt1_product {

            @Nested
            @DisplayName("추가하려는 product 의 내용에 null 이나 공백이 없고")
            class Context_valid_input {

                @Nested
                @DisplayName("id 와 일치하는 물품이 존재할때")
                class Context_exist_matched_product {

                    @Test
                    @DisplayName("일치하는 물품의 정보를 수정한 뒤 반환한다.")
                    void it_returns_updated_product() {

                    }
                }

                @Nested
                @DisplayName("id 와 일치하는 물품이 존재하지 않을 때")
                class Context_not_exist_matched_product {

                    @Test
                    @DisplayName("예외를 발생시킨다.")
                    void it_throws_not_found_exception() {

                    }
                }
            }

            @Nested
            @DisplayName("추가하려는 product 의 내용에 null 이나 공백이 있을 때")
            class Context_invalid_input {

                @Test
                @DisplayName("예외를 발생시킨다.")
                void it_throws_bad_request_exception() {

                }
            }

        }


        @Nested
        @DisplayName("찾고자 하는 product id 가 1미만일 때")
        class Context_id_lt1_product {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_bad_request_exception() {

            }
        }
    }


    @Nested
    @DisplayName("delete() 매소드는")
    class Describe_delete {


        @Nested
        @DisplayName("product id가 1이상이고")
        class Context_id_gt_1 {

            @Nested
            @DisplayName("id 와 일치하는 물품이 존재할때")
            class Context_exist_matched_product {

                @Test
                @DisplayName("일치하는 물품을 삭제한 뒤 204를 반환한다.")
                void it_deletes_product_and_returns_204() {

                }
            }

            @Nested
            @DisplayName("id 와 일치하는 물품이 존재하지 않을 때")
            class Context_not_exist_matched_product {

                @Test
                @DisplayName("예외를 발생시킨다.")
                void it_throws_not_found_exception() {

                }
            }
        }

        @Nested
        @DisplayName("찾고자 하는 product id 가 1미만일 때")
        class Context_id_lt1_product {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void it_throws_bad_request_exception() {

            }
        }
    }

}
