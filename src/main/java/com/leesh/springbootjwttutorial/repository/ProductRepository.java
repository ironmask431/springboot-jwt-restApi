package com.leesh.springbootjwttutorial.repository;

import com.leesh.springbootjwttutorial.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
