package com.codesoom.assignment.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Product {
    @Id
    @SequenceGenerator(name = "id_seq", sequenceName = "item_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;
    private String name;
    private String maker;
    private int price;
    private String image;
}
