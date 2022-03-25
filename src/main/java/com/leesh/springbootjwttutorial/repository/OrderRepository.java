package com.leesh.springbootjwttutorial.repository;

import com.leesh.springbootjwttutorial.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
