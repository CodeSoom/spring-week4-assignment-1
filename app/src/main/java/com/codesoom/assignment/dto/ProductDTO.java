package com.codesoom.assignment.dto;

import com.codesoom.assignment.model.Product;

import lombok.Getter;

public class ProductDTO {

	@Getter
	public static class CreateProduct {
		private String name;
		private String maker;
		private int price;
		private String imageUrl;
		public CreateProduct(String name, String maker, int price, String imageUrl) {
			this.name = name;
			this.maker = maker;
			this.price = price;
			this.imageUrl = imageUrl;
		}
	}

	@Getter
	public static class UpdateProduct {
		private String name;
		private String maker;
		private int price;
		private String imageUrl;
	}

	@Getter
	public static class Response {
		private final int id;
		private final String name;
		private final String maker;
		private final int price;
		private final String imageUrl;

		public Response(int id, String name, String maker, int price, String imageUrl) {
			this.id = id;
			this.name = name;
			this.maker = maker;
			this.price = price;
			this.imageUrl = imageUrl;
		}

		public static Response of(Product product) {
			return new Response(product.getId(), product.getName(), product.getMaker(), product.getPrice(),
				product.getImageUrl());
		}
	}

}
