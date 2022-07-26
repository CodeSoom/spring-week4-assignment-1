package com.codesoom.assignment.domain;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public abstract class CatToyRepository{

    public abstract List<CatToy> findAll();
    public abstract CatToy find(Long id);
    public abstract CatToy save(CatToy toy);
    public abstract CatToy delete(CatToy toy);

}
