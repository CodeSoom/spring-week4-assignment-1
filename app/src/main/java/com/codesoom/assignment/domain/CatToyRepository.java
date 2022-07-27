package com.codesoom.assignment.domain;

import java.util.List;

public interface CatToyRepository {
    List<CatToy> findAll();

    void deleteAll();
}
