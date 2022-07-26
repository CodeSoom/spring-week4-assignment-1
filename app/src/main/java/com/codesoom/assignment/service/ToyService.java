package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyDto;

public interface ToyService {
    CatToy create(CatToyDto catToyDto);
}
