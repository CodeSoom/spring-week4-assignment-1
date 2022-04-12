package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.Toy;
import com.codesoom.assignment.domain.ToyDto;

public interface ToyUpdateService {

    Toy update(Long id, ToyDto toyDto);

}
