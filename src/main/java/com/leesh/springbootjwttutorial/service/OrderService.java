package com.leesh.springbootjwttutorial.service;

import com.leesh.springbootjwttutorial.dto.OrderDto;
import com.leesh.springbootjwttutorial.dto.ProductDto;
import com.leesh.springbootjwttutorial.entity.Order;
import com.leesh.springbootjwttutorial.entity.Product;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.repository.OrderRepository;
import com.leesh.springbootjwttutorial.repository.ProductRepository;
import com.leesh.springbootjwttutorial.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    //주문목록 전제 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findAll(){
        return orderRepository.findAll().stream()
                .map(order -> {return new OrderDto(order);}).collect(Collectors.toList());
    }

    //주문번호로 주문 조회
    @Transactional(readOnly = true)
    public OrderDto findById(Long ord_id){
        Order order = orderRepository.findById(ord_id)
                .orElseThrow(()-> new IllegalArgumentException("해당 주문번호가 없습니다. ord_id="+ord_id));

        OrderDto orderDto = new OrderDto(order);

        Product product = productRepository.findById(orderDto.getPrdId())
                .orElseThrow(()-> new IllegalArgumentException("해당 상품번호가 없습니다. prd_id="+orderDto.getPrdId()));
        ProductDto productDto = new ProductDto(product);

        orderDto.setProductDto(productDto);

        return orderDto;
    }
}
