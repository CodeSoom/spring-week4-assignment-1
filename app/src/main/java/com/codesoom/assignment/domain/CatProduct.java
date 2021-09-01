package com.codesoom.assignment.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CatProduct {
  @Id
  @GeneratedValue
  @Column(name = "product_id")
  private Long id;

  @Column(name = "product_name")
  private String name;

  private String maker;

  private BigDecimal price;

  private String imgUrl;

}
