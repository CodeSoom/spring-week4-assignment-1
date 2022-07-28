package com.codesoom.assignment.application;

import com.codesoom.assignment.CatToyNotFoundException;
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
        return catToyRepository.findById(id)
                .orElseThrow(() ->new CatToyNotFoundException(id));
    }

    public CatToy createToy(CatToy source){

        return catToyRepository.save(source);
    }

    /**
     * TODO
     * - 특정 항목만 변경해도 업데이트가 되어야한다.
     * - 빈 항목이 있다면 업데이트 하지 않고 그대로 유지
     * - 항목이 알맞은 형식일 때만 업데이트해야한다. (price - long)
     */
    public CatToy updateToy(Long id, CatToy source){

        CatToy toy = getToys(id);


        if(!source.getName().isBlank())
            toy.setName(source.getName());

        if(!source.getMaker().isBlank())
            toy.setMaker(source.getMaker());

        if(!source.getImg_url().isBlank())
            toy.setImg_url(source.getImg_url());

        return catToyRepository.save(toy);
    }

    public CatToy deleteToy(Long id){
        CatToy toy = catToyRepository.findById(id)
                .orElseThrow(() -> new CatToyNotFoundException(id));
        catToyRepository.delete(toy);
        return toy;
    }

}
