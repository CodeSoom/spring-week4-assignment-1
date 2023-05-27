package com.codesoom.assignment.controller;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerWebTest {

	@Autowired
	private MockMvc mockMvc;

	private ObjectMapper objectMapper;
	@MockBean
	private ProductService productService;

	@BeforeEach
	public void setUp() {
		objectMapper = new ObjectMapper();

		Product product = new Product();
		product.setName("쥐방울");
		product.setMaker("냥냥상회");
		product.setPrice(5000);
		product.setImageUrl("");

		when(productService.getAll()).thenReturn(Collections.singletonList(new Product()));
		when(productService.getProduct(1L)).thenReturn(product);
		when(productService.getProduct(100L)).thenThrow(new ProductNotFoundException(100L));
		when(productService.createProduct(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
	}

	@Test
	void getAll() throws Exception {
		mockMvc.perform(get("/products"))
				.andExpect(status().isOk());
	}

	@Test
	void getById() throws Exception {
		mockMvc.perform(get("/products/1"))
				.andExpect(status().isOk());
	}

	@Test
	void getByInvalidId() throws Exception {
		mockMvc.perform(get("/products/100"))
				.andExpect(status().isNotFound());
	}

	@Test
	void createTest() throws Exception {
		Product product = new Product();
		product.setName("쥐방울");
		product.setMaker("냥냥상회");
		product.setPrice(5000);
		product.setImageUrl("");

		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("쥐방울")));
	}

	@Test
	void updateTest() throws Exception {
		Product product = new Product();
		product.setName("쥐방울_update");
		product.setMaker("냥냥상회2");
		product.setPrice(7000);

		mockMvc.perform(patch("/products/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(product)))
				.andExpect(status().isOk());

	}

	@Test
	void deleteTest() throws Exception {
		mockMvc.perform(delete("/products/1"))
				.andExpect(status().isNoContent());
	}
}
