package com.codesoom.assignment.application;


import com.codesoom.assignment.TaskNotFoundException;
import com.codesoom.assignment.domain.JpaToyRepository;
import com.codesoom.assignment.domain.Toy;
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

    private final JpaToyRepository jpaToyRepository;

    /**
     * 생성자에 JpaToyRepository 주입
     * @param jpaToyRepository CrudRepository 상속받은 인터페이스
     */
    public ToyService(JpaToyRepository jpaToyRepository) {
        this.jpaToyRepository = jpaToyRepository;
    }

    /**
     * 모든 Toy 목록 가져옴
     * @return 모든 Toy 를 담고있는 list 반환
     */
    public List<Toy> getAllToy(){
        return jpaToyRepository.findAll();
    }

    /**
     * 개별 toy 반환
     * @param id 찾고자 하는 toy id
     * @return
     */
    public Toy getToyById(Long id) {
        return jpaToyRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    /**
     * 새로운 Toy 생성
     * @param source Toy 인스턴스
     * @return toy 저장
     */
    public Toy createToy(Toy source) {
        Toy toy = new Toy();
        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());

        return jpaToyRepository.save(toy);
    }

    /**
     * toy 정보 업데이트
     * @param id update 할 toy의 id
     * @param source Toy 인스턴스
     * @return 업데이트한 toy
     */
    public Toy updateToy(Long id, Toy source) {

        Toy toy = jpaToyRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());

        return toy;
    }

    /**
     * toy 삭제
     * @param id 삭제할 toy id
     */
    public void deleteToy(Long id) {
        Toy toy = jpaToyRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        jpaToyRepository.delete(toy);
    }
}
