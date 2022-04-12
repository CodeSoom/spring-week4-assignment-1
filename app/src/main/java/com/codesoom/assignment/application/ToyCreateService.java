package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyDto;


/**
 * 장난감을 생성하는 역할만 담당합니다.
 */
public interface ToyCreateService {

    /**
     * 장난감을 생성합니다.
     *
     * @param toyDto 사용자가 입력한 데이터
     * @return 생성된 결과
     */
    Toy create(ToyDto toyDto);

}
