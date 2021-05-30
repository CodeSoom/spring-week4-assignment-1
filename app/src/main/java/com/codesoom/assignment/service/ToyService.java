package com.codesoom.assignment.service;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.dto.ToySaveRequest;
import com.codesoom.assignment.repository.ToyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 장난감 정보에 대한 작업을 처리한다.
 */
@Service
public class ToyService {

    private ToyRepository toyRepository;

    public ToyService(ToyRepository toyRepository) {
        this.toyRepository = toyRepository;
    }

    /**
     * 장난감 목록을 반환한다.
     *
     * @return 장난감 목록
     */
    public List<Toy> list() {
        return (List<Toy>) toyRepository.findAll();
    }

    /**
     * 장난감 정보를 생성하고, 생성된 결과를 리턴합니다.
     *
     * @param toy 장난감 정보 객체
     * @return 장난감 정보
     */
    public Toy create(Toy toy) {
        return this.toyRepository.save(toy);
    }
}
