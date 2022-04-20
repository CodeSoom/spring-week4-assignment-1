package com.codesoom.assignment;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.lang.annotation.*;
import java.nio.charset.StandardCharsets;

/**
 * @AutoConfigureMockMvc 에 UTF-8 인코딩 필터를 적용한다.
 *
 * Spring 에 존재하던 APPLICATION_JSON_UTF8 상수가 Deprecated 되어
 * @AutoConfigureMockMvc 를 이용할 경우, 한글 인코딩 문제가 발생한다.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AutoConfigureMockMvc
@Import(Utf8MockMvc.Config.class)
public @interface Utf8MockMvc {
    class Config {
        @Bean
        public CharacterEncodingFilter characterEncodingFilter() {
            return new CharacterEncodingFilter(StandardCharsets.UTF_8.name(), true);
        }
    }
}
