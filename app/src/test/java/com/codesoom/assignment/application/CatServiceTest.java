package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Cat;
import com.codesoom.assignment.domain.CatRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("CatService")
class CatServiceTest {

    @InjectMocks
    private CatService catService;

    @Mock
    private CatRepository catRepository;

    String name;
    String maker;
    Long price;
    String imgUrl;

    @Nested
    @DisplayName("Create 메소드는")
    class Create {

        @Nested
        @DisplayName("모든 파라미터가 존재할 경우")
        class Exist_all_parameter {

            @Test
            @DisplayName("Cat을 만들어 Repository 에 저장한다.")
            public void save_cat() {
                Mockito.verify(catRepository).save(any());
            }

        }

    }
}
