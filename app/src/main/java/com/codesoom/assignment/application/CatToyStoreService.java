package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.exception.NoDataException;
import com.codesoom.assignment.repository.CatToyStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CatToyStoreService {

    private CatToyStoreRepository catToyStoreRepository;

    CatToyStoreService(CatToyStoreRepository catToyStoreRepositor){
        this.catToyStoreRepository = catToyStoreRepositor;
    }

    public List<CatToy> list(){

        return catToyStoreRepository.findAll();
    }

    public CatToy detail(Long id){

        return catToyStoreRepository.findById(id)
                .orElseThrow(() -> new NoDataException(id));

    }


    public CatToy create(CatToy resource){

        CatToy catToy = new CatToy();
        catToy.setName(resource.getName());
        catToy.setMaker(resource.getMaker());
        catToy.setPrice(resource.getPrice());
        catToy.setImageUrl(resource.getImageUrl());

        return catToyStoreRepository.save(catToy);
    }


    public CatToy update(@PathVariable Long id, CatToy resource){

        CatToy catToy = catToyStoreRepository.findById(id)
                .orElseThrow(() -> new NoDataException(id));

        catToy.setName(resource.getName());
        catToy.setMaker(resource.getMaker());
        catToy.setPrice(resource.getPrice());
        catToy.setImageUrl(resource.getImageUrl());

        return catToy;
    }


    public CatToy delete(@PathVariable Long id){

        CatToy catToy = catToyStoreRepository.findById(id)
                .orElseThrow(() -> new NoDataException(id));

        catToyStoreRepository.deleteById(id);

        return catToy;
    }
}
