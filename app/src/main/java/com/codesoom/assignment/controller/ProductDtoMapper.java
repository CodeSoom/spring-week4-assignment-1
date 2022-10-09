package com.codesoom.assignment.controller;

import com.codesoom.assignment.controller.ProductDto.RequestParam;
import com.codesoom.assignment.domain.ProductCommand;

public interface ProductDtoMapper {
    ProductCommand.Register of(RequestParam request);

    ProductCommand.UpdateReq of(Long id, RequestParam requestParam);
}
