package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class Product {

	private String name;
	private String maker;
	private long price;
	private String imageUrl;

	public Product updateDetail(Product product) {
		this.name = product.getName();
		this.maker = product.getName();
		this.price = product.getPrice();
		this.imageUrl = product.getImageUrl();

		return this;
	}
}
