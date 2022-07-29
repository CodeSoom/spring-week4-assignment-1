package com.codesoom.assignment;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;

public class ToyTestHelper {
    public final String givenToyName = "낚시물고기";
    public final String givenMaker = "나이키";
    public final Integer givenPrice = 5000;
    public final String givenUrl = "url";

    public final String changedToyName = "변경된 낚시물고기";
    public final String changedMaker = "변경된 나이키";
    public final Integer changedPrice = 10;
    public final String changedUrl = "변경된 url";

    public final CatToyDto givenToyDto = new CatToyDto(givenToyName, givenMaker, givenPrice, givenUrl);

    public final CatToyDto givenToyToChangeDto = new CatToyDto(changedToyName, changedMaker, changedPrice, changedUrl);

    public CatToy changedToy(Long id) {
        return new CatToy(id, changedToyName, changedMaker, changedPrice, changedUrl);
    }

}
