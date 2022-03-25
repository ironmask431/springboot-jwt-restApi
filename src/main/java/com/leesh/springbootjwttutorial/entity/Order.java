package com.leesh.springbootjwttutorial.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity //DB테이블과 1:1 매치
@Table(name="order_table") //테이블명 지정
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
