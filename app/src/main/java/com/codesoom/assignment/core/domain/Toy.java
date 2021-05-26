package com.codesoom.assignment.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 고양이 장난감
 */
@Entity
@Getter @Setter
public class Toy {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String brand;

    private String price;

    private String image;

}
