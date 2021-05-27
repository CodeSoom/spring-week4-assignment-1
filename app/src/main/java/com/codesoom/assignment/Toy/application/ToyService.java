package com.codesoom.assignment.Toy.application;


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
     * 생성자에 ToyJpaRepository 주입
     *
     * @param toyJpaRepository CrudRepository 상속받은 인터페이스
     */
    public ToyService(ToyJpaRepository toyJpaRepository) {
        this.toyJpaRepository = toyJpaRepository;
    }

    /**
     * 모든 Toy 목록 가져옴
     *
     * @return 모든 Toy 를 담고있는 list 반환
     */
    public List<Toy> getAllToy() {
        return toyJpaRepository.findAll();
    }

    /**
     * 개별 toy 반환
     *
     * @param id 찾고자 하는 toy id
     * @return
     */
    public Toy getToyById(Long id) {
        return toyJpaRepository.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));
    }

    /**
     * 새로운 Toy 생성
     *
     * @param source Toy 인스턴스
     * @return toy 저장
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
     * toy 정보 업데이트
     *
     * @param id     update 할 toy의 id
     * @param source Toy 인스턴스
     * @return 업데이트한 toy
     */
    public Toy updateToy(Long id, Toy source) {

        Toy toy = toyJpaRepository.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));

        toy.setName(source.getName());
        toy.setMaker(source.getMaker());
        toy.setPrice(source.getPrice());
        toy.setImageUrl(source.getImageUrl());

        return toy;
    }

    /**
     * toy 삭제
     *
     * @param id 삭제할 toy id
     */
    public Toy deleteToy(Long id) {
        Toy toy = toyJpaRepository.findById(id)
                .orElseThrow(() -> new ToyNotFoundException(id));
        toyJpaRepository.delete(toy);
        return toy;
    }
}
