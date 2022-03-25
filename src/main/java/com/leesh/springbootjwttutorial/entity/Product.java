package com.leesh.springbootjwttutorial.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="product")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name="prd_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long prdId;

    @Column(name="prd_nm", length = 100)
    private String prdNm;

    @Column(name="price")
    private int price;
}
