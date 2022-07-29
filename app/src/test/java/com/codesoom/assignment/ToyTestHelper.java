package com.codesoom.assignment;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;

public class ToyTestHelper {
    public static final Long impossibleId = -1L;
    public static final String givenToyName = "낚시물고기";
    public static final String givenMaker = "나이키";
    public static final Integer givenPrice = 5000;
    public static final String givenUrl = "url";

    public static final String changedToyName = "변경된 낚시물고기";
    public static final String changedMaker = "변경된 나이키";
    public static final Integer changedPrice = 10;
    public static final String changedUrl = "변경된 url";

    public static final CatToyDto toyToCreateDto = new CatToyDto(givenToyName, givenMaker, givenPrice, givenUrl);

    public static final CatToyDto toyToChangeDto = new CatToyDto(changedToyName, changedMaker, changedPrice, changedUrl);

    public static CatToy createdToy(Long id) {
        return new CatToy(id, givenToyName, givenMaker, givenPrice, givenUrl);
    }

    public static CatToy changedToy(Long id) {
        return new CatToy(id, changedToyName, changedMaker, changedPrice, changedUrl);
    }
}
