package com.codesoom.assignment.application;

import com.codesoom.assignment.domain.CatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatService {
    private final CatRepository catRepository;

}
