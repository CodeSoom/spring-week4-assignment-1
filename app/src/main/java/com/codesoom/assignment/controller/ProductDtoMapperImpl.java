package com.codesoom.assignment.controller;

import com.codesoom.assignment.controller.ProductDto.RequestParam;
import com.codesoom.assignment.domain.ProductCommand.Register;
import com.codesoom.assignment.domain.ProductCommand.UpdateReq;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapperImpl implements ProductDtoMapper {
    public Register of(RequestParam request) {
        if (request == null) {
            return null;
        }

        return Register.builder()
                .name(request.getName())
                .maker(request.getMaker())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public UpdateReq of(Long id, RequestParam requestParam) {
        if (id == null || requestParam == null) {
            return null;
        }

        return UpdateReq.builder()
                .id(id)
                .name(requestParam.getName())
                .maker(requestParam.getMaker())
                .price(requestParam.getPrice())
                .imageUrl(requestParam.getImageUrl())
                .build();
    }
}
