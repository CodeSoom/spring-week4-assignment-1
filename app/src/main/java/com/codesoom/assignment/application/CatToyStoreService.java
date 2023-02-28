package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class CatToyStoreService {


    public List<CatToy> list(){

        return null;
    }

    public CatToy detail(Long id){

        return null;
    }


    public CatToy create(CatToy catToy){

        return null;
    }


    public CatToy update(@PathVariable Long id, CatToy catToy){

        return null;
    }


    public void delete(@PathVariable Long id){

    }
}
