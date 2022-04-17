package com.codesoom.assignment.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@CrossOrigin
@RequestMapping("/products")
@RestController
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProductController {
}
