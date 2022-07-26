package com.codesoom.assignment.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CatToyTest {

    @Test
    @DisplayName("장난감은 이름, 메이커, 가격, 사진 경로를 가지고 있다.")
    void makeCatToy() {
        CatToy catToy = new CatToy(1L, "장난감 뱀", "애옹이네 장난감", 5000, "url");
    }
}
