package com.codesoom.assignment;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;

public class ToyTestHelper {
    public static final Long IMPOSSIBLE_ID = -1L;
    public static final String GIVEN_TOY_NAME = "낚시물고기";
    public static final String GIVEN_MAKER = "나이키";
    public static final Integer GIVEN_PRICE = 5000;
    public static final String GIVEN_URL = "url";

    public static final String CHANGED_TOY_NAME = "변경된 낚시물고기";
    public static final String CHANGED_MAKER = "변경된 나이키";
    public static final Integer CHANGED_PRICE = 10;
    public static final String CHANGED_URL = "변경된 url";

    public static final CatToyDto TOY_TO_CREATE_DTO = new CatToyDto(GIVEN_TOY_NAME, GIVEN_MAKER, GIVEN_PRICE, GIVEN_URL);

    public static final CatToyDto TOY_TO_CHANGE_DTO = new CatToyDto(CHANGED_TOY_NAME, CHANGED_MAKER, CHANGED_PRICE, CHANGED_URL);

    public static CatToy createdToy(Long id) {
        return new CatToy(id, GIVEN_TOY_NAME, GIVEN_MAKER, GIVEN_PRICE, GIVEN_URL);
    }

    public static CatToy changedToy(Long id) {
        return new CatToy(id, CHANGED_TOY_NAME, CHANGED_MAKER, CHANGED_PRICE, CHANGED_URL);
    }
}
