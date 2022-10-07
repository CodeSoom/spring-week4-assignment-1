package com.codesoom.assignment.controller;

import com.codesoom.assignment.controller.ProductDto.RequestParam;
import com.codesoom.assignment.domain.ProductCommand.Register;
import com.codesoom.assignment.domain.ProductCommand.Register.RegisterBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoMapper {
    public Register of(RequestParam request) {
        if (request == null) {
            return null;
        }

        RegisterBuilder register = Register.builder();

        register.id(request.getId());
        register.name(request.getName());
        register.maker(request.getMaker());
        register.price(request.getPrice());
        register.imageUrl(request.getImageUrl());

        return register.build();
    }

    public Register of(Long id, RequestParam requestParam) {
        if (id == null || requestParam == null) {
            return null;
        }

        RegisterBuilder register = Register.builder();
        register.id(id);
        register.name(requestParam.getName());
        register.maker(requestParam.getMaker());
        register.price(requestParam.getPrice());
        register.imageUrl(requestParam.getImageUrl());

        return register.build();
    }
}
