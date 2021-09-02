package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.dto.CatToyModel;
import com.codesoom.assignment.repository.CatToyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;

/**
 * 고양이 장난감 생성, 조회, 수정, 삭제한다.
 */
@Service
@Transactional
public class CatToyService {

    private final CatToyRepository catToyRepository;

    public CatToyService(CatToyRepository catToyRepository) {
        this.catToyRepository = catToyRepository;
    }

    /**
     * 고양이 장난감을 생성한다.
     * @param catToyModel
     * @return 고양이 장난감
     */
    @ResponseStatus(HttpStatus.CREATED)
    public CatToyModel createCatToy(CatToyModel catToyModel) {
        CatToy catToy = new CatToy(catToyModel.name(), catToyModel.maker(), catToyModel.price(), catToyModel.imageUrl());
        return new CatToyModel(catToyRepository.save(catToy));
    }
}
