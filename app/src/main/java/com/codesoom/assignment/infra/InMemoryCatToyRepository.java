package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCatToyRepository implements CatToyRepository {

    @Override
    public List<CatToy> getList() {
        return new ArrayList();
    }

    @Override
    public void removeAll() {

    }
}
