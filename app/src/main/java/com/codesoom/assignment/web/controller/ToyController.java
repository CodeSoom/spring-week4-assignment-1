package com.codesoom.assignment.web.controller;

import com.codesoom.assignment.core.application.ToyService;
import com.codesoom.assignment.core.domain.Toy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 고양이 장난감 물품에 대한 Request 처리하여 Response를 반환합니다.
 */
@RestController
@CrossOrigin
@RequestMapping("/toys")
public class ToyController {

    private final ToyService toyService;

    public ToyController(ToyService toyService) {
        this.toyService = toyService;
    }

    /**
     * 모든 고양이 장난감 목록을 반환합니다.
     * @return 고양이 장난감 목록
     */
    @GetMapping
    public List<Toy> toys() {
        return toyService.toys();
    }

}
