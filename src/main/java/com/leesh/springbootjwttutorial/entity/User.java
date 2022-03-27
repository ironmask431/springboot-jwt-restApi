package com.leesh.springbootjwttutorial.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * 유저 entity
 */
@Entity
@Table(name="user")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long userId;

    @Column(name="email", length = 50, unique = true)
    private String email;

    @JsonIgnore //JSON 리턴 시 생략됨.
    @Column(name="password", length = 100)
    private String password;

    @JsonIgnore
    @Column(name="activated")
    private boolean activated;

    @ManyToMany //테이블간 다대다 관계
    @JoinTable(
            name="user_authority",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name="authority_name", referencedColumnName = "authority_name")}
    )
    private Set<Authority> authorities;


}
