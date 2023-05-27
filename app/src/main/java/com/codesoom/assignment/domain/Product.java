package com.codesoom.assignment.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@EqualsAndHashCode(exclude = {"id"})
public class Product {

	private long id;
	private String name;
	private String maker;
	private long price;
	private String imageUrl;

	public Product updateDetail(Product product) {
		this.name = product.getName();
		this.maker = product.getMaker();
		this.price = product.getPrice();
		this.imageUrl = product.getImageUrl();

		return this;
	}
}
