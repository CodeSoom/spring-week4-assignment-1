package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

  @Autowired
  MockMvc mockMvc;

  private ProductController controller;
  private ProductService service;

  @Autowired
  private ProductRepository repository;

  @BeforeEach
  void setUp() throws Exception {
    service = new ProductService(repository);
    controller = new ProductController(service);

  }

  @Test
  void getList() throws Exception {
    mockMvc.perform(get("/products"))
        .andExpect(status().isOk());
  }

}
