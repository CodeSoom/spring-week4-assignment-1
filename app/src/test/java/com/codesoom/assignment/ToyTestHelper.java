package com.codesoom.assignment;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;

public class ToyTestHelper {
    public final Long givenId = 1L;
    public final String givenToyName = "낚시물고기";
    public final String givenMaker = "나이키";
    public final Integer givenPrice = 5000;
    public final String givenUrl = "url";

    public final CatToyDto givenToyDto = new CatToyDto(givenToyName, givenMaker, givenPrice, givenUrl);
    public final CatToy expectToy = new CatToy(givenId, givenToyName, givenMaker, givenPrice, givenUrl);

}
