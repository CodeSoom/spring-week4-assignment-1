package com.codesoom.assignment.infra;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCatToyRepository implements CatToyRepository {
    private final ArrayList<CatToy> catToys = new ArrayList();

    @Override
    public List<CatToy> getList() {
        return catToys;
    }

    @Override
    public void removeAll() {
        catToys.clear();
    }
}