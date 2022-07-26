package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.domain.CatToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatToyService {

    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    public List<CatToy> getCatToys(){
        return catToyRepository.findAll();
    }

    public CatToy getToys(Long id){
        return catToyRepository.find(id);
    }

    public CatToy createToy(CatToy source){
        CatToy toy = new CatToy(source.getName(), source.getMaker(), source.getPrice(), source.getImg_url());
        return catToyRepository.save(toy);
    }

    //추가적인 구현이 필요합니다.
    public CatToy updateToy(Long id, CatToy source){

        CatToy toy = getToys(id);
        toy.setName(source.getName());

        return catToyRepository.save(toy);
    }

    public CatToy deleteToy(Long id){
        return catToyRepository.delete(getToys(id));
    }

}
