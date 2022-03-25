package com.leesh.springbootjwttutorial.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="order_table")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @Column(name="ord_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long ordId;

    @Column(name="user_id")
    private Long userId;

    @Column(name="prd_id")
    private Long prdId;

}
