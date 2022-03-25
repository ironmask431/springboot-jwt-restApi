package com.leesh.springbootjwttutorial.controller;

import com.leesh.springbootjwttutorial.dto.OrderDto;
import com.leesh.springbootjwttutorial.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    //전체 주문 조회
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<OrderDto> findAll(){
        return orderService.findAll();
    }

    //주문번호로 주문 조회
    @GetMapping("/ordId/{ordId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OrderDto findById(@PathVariable Long ordId){
        return orderService.findById(ordId);
    }

    //userId 로 주문 조회
    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<OrderDto> findByUserId(@PathVariable Long userId){
        return orderService.findByUserId(userId);
    }

    //현재 인증된 사용자의 주문 조회 (나의 주문조회)
    @GetMapping("/myOrder")
    public List<OrderDto> findByMyUserId(){
        return orderService.findByMyUserId();
    }

    //주문입력
    @PostMapping("/save")
    public OrderDto save(@Valid @RequestBody OrderDto orderDto){
        return orderService.save(orderDto);
    }
}
