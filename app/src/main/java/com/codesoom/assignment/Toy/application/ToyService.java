package com.codesoom.assignment.Toy.application;


import com.codesoom.assignment.Task.TaskNotFoundException;
import com.codesoom.assignment.Toy.ToyNotFoundException;
import com.codesoom.assignment.Toy.domain.Toy;
import com.codesoom.assignment.Toy.domain.ToyJpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Toy Entity의 REST API
 * GET - getAllToy(), getToyById()
 * POST - createToy()
 * UPDATE - updateToy()
 * DELETE - deleteToy()
 */
@Service
@Transactional
public class ToyService {

    private final ToyJpaRepository toyJpaRepository;

    /**
     * 생성자에 ToyJpaRepository 주입합니다.
     * @param toyJpaRepository CrudRepository 상속받은 인터페이스
     */
    public ToyService(ToyJpaRepository toyJpaRepository) {
        this.toyJpaRepository = toyJpaRepository;
    }

    /**
     * 모든 Toy 목록을 가져와 리턴합니다.
     * @return 모든 Toy 를 담고있는 list
     */
    public List<Toy> getAllToy(){
        return toyJpaRepository.findAll();
    }

    /**
     * 요청한 id에 맞는 Toy 를 리턴합니다.
     * @param id 요청 id
     * @return 해당 id의 Toy 객체
     */
    public Toy getToy(Long id) {
        return toyJpaRepository.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));
    }

    /**
     * 새로운 Toy 객체를 생성합니다.
     * @param source Toy 객체
     * @return 생성된 toy 객체
     */
    public Toy createToy(Toy source) {
        Toy toy = new Toy();
        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());

        return toyJpaRepository.save(toy);
    }

    /**
     * 기존 toy 의 정보를 업데이트 합니다.
     * @param id update 할 toy 의 id
     * @param source Toy 객체
     * @return 업데이트한 toy 객체
     */
    public Toy updateToy(Long id, Toy source) {

        Toy toy = toyJpaRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());

        return toy;
    }

    /**
     * 저장된 toy 를 삭제합니다.
     * @param id 삭제할 toy id
     * @return 삭제한 toy 객체
     */
    public Toy deleteToy(Long id) {
        Toy toy = toyJpaRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        toyJpaRepository.delete(toy);
        return toy;
    }
}
