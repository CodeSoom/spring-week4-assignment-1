package com.codesoom.assignment.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter @Setter
public class Product {
  @Id
  @GeneratedValue
  @Column(name = "product_id")
  private Long id;

  @Column(name = "product_name")
  private String name;

  private String maker;

  private int price;

  private String imgUrl;

}
