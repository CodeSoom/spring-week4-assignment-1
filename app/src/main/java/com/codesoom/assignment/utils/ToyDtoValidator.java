package com.codesoom.assignment.utils;

import com.codesoom.assignment.domain.ToyDto;
import com.codesoom.assignment.exceptions.InvalidToyDtoException;

public class ToyDtoValidator {

    public static void validateDto(ToyDto dto) {
        validateNameField(dto);
        validateMakerField(dto);
        vallidatePriceField(dto);
        validateImageField(dto);
    }

    private static void validateNameField(ToyDto dto) {
        final String name = dto.getName();
        if (name == null || name.isBlank()) {
            throw new InvalidToyDtoException(name + "은 이름으로 허용되지 않습니다.");
        }
    }

    private static void validateMakerField(ToyDto dto) {
        final String maker = dto.getMaker();
        if (maker == null || maker.isBlank()) {
            throw new InvalidToyDtoException(maker + "은 메이커로 허용되지 않습니다.");
        }
    }

    private static void vallidatePriceField(ToyDto dto) {
        final Integer price = dto.getPrice();
        if (price == null || price < 0) {
            throw new InvalidToyDtoException(price + "은 가격으로 허용되지 않습니다.");
        }
    }

    private static void validateImageField(ToyDto dto) {
        final String image = dto.getImage();
        if (image == null || image.isBlank()) {
            throw new InvalidToyDtoException(image + "은 이미지로 허용되지 않습니다.");
        }
    }
}
