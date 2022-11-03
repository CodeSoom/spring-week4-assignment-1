package com.codesoom.assignment.util;

import com.codesoom.assignment.products.controllers.dto.request.ProductCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.codesoom.assignment.support.ProductFixture.TOY_1;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Jackson을 사용한 JsonUtil")
class JsonUtilTest {
    @Test
    @DisplayName("데이터를 직렬화한 후 역 직렬화하면 기존 데이터를 리턴한다")
    void write_read() throws IOException {
        ProductCreateRequest originData = TOY_1.요청_데이터_생성();

        String requestStr = JsonUtil.writeValue(originData);

        ProductCreateRequest resultData = JsonUtil.readValue(requestStr, ProductCreateRequest.class);

        assertThat(originData).isEqualTo(resultData);
    }
}
