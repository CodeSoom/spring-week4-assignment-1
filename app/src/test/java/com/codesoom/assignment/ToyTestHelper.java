package com.codesoom.assignment;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;

public class ToyTestHelper {
    private static final String givenToyName = "낚시물고기";
    private static final String givenMaker = "나이키";
    private static final Integer givenPrice = 5000;
    private static final String givenUrl = "url";

    private static final String changedToyName = "변경된 낚시물고기";
    private static final String changedMaker = "변경된 나이키";
    private static final Integer changedPrice = 10;
    private static final String changedUrl = "변경된 url";

    public static final CatToyDto toyToCreateDto = new CatToyDto(givenToyName, givenMaker, givenPrice, givenUrl);

    public static final CatToyDto toyToChangeDto = new CatToyDto(changedToyName, changedMaker, changedPrice, changedUrl);

    public CatToy createdToy(Long id) {
        return new CatToy(id, changedToyName, changedMaker, changedPrice, changedUrl);
    }

    public CatToy changedToy(Long id) {
        return new CatToy(id, changedToyName, changedMaker, changedPrice, changedUrl);
    }
}
