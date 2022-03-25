package com.leesh.springbootjwttutorial.controller;

import com.leesh.springbootjwttutorial.dto.OrderDto;
import com.leesh.springbootjwttutorial.entity.Order;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService orderService;

    //전체 주문 조회
    @GetMapping("/order/")
    public List<OrderDto> findAll(){
        return orderService.findAll();
    }

    //주문번호로 주문 조회
    @GetMapping("/order/{ordId}")
    public OrderDto findById(@PathVariable Long ordId){
        return orderService.findById(ordId);
    }
}
