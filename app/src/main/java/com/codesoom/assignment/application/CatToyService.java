package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatToy;
import com.codesoom.assignment.dto.CatToyModel;
import com.codesoom.assignment.exception.CatToyNotFoundException;
import com.codesoom.assignment.repository.CatToyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.util.List;

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

    /**
     * 고양이 장난감 리스트를 조회하여 리턴한다.
     * @return 고양이 장난감 리스트
     */
    public List<CatToyModel> selectCatToyList() {
        return CatToyModel.ofList(catToyRepository.findAll());
    }

    /**
     * 고양이 장난감 하나를 조회하여 리턴한다.
     * @param id
     * @return 고양이 장난감
     */
    public CatToyModel selectCatToy(Long id) {
        return new CatToyModel(findCatToy(id));
    }


    /**
     * 고양이 장난감이 있으면 조회, 없으면 CatToyNotFoundException 발생
     * @param id
     * @exception CatToyNotFoundException
     * @return 고양이 장난감
     */
    private CatToy findCatToy(Long id) {
        return catToyRepository.findById(id).orElseThrow(CatToyNotFoundException::new);
    }
}
