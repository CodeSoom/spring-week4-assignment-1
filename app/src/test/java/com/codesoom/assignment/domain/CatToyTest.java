package com.codesoom.assignment.domain;

import com.codesoom.assignment.ToyTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CatToyTest {
    @Test
    @DisplayName("장난감은 이름, 메이커, 가격, 사진 경로를 가지고 있다.")
    void makeCatToy() {
        CatToy catToy = new CatToy(
                ToyTestHelper.POSSIBLE_ID,
                ToyTestHelper.GIVEN_TOY_NAME,
                ToyTestHelper.GIVEN_MAKER,
                ToyTestHelper.GIVEN_PRICE,
                ToyTestHelper.GIVEN_URL
        );

        assertThat(catToy).isEqualTo(ToyTestHelper.createdToy(ToyTestHelper.POSSIBLE_ID));
    }

    @Test
    @DisplayName("update 메서드는 장난감을 변경한다")
    void updateChangeToy() {
        CatToy catToy = ToyTestHelper.MADE_TOY;
        catToy.update(ToyTestHelper.CHANGED_TOY_NAME,
                        ToyTestHelper.CHANGED_MAKER,
                        ToyTestHelper.CHANGED_PRICE,
                        ToyTestHelper.CHANGED_URL);

        assertThat(catToy).isEqualTo(ToyTestHelper.changedToy(catToy.getId()));
    }
}
