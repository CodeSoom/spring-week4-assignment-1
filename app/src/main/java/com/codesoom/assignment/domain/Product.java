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

}
