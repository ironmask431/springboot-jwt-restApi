package com.leesh.springbootjwttutorial.service;

import com.leesh.springbootjwttutorial.dto.OrderDto;
import com.leesh.springbootjwttutorial.dto.ProductDto;
import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.Order;
import com.leesh.springbootjwttutorial.entity.Product;
import com.leesh.springbootjwttutorial.entity.User;
import com.leesh.springbootjwttutorial.repository.OrderRepository;
import com.leesh.springbootjwttutorial.repository.ProductRepository;
import com.leesh.springbootjwttutorial.repository.UserRepository;
import com.leesh.springbootjwttutorial.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository; //productService 로 수정하기
    private final UserService userService;
    private final ProductService productService;

    //주문목록 전제 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findAll(){
        List<OrderDto> orderDtoList = orderRepository.findAll().stream()
                .map(order -> {return new OrderDto(order);}).collect(Collectors.toList());

        for(OrderDto orderDto : orderDtoList){
            ProductDto productDto = productService.findById(orderDto.getPrdId());
            //조회한 상품정보 orderDto에 입력
            orderDto.setProductDto(productDto);
        }

        return orderDtoList;
    }

    //주문번호로 주문 조회
    @Transactional(readOnly = true)
    public OrderDto findById(Long ord_id){
        //ord_id로 주문정보 조회
        Order order = orderRepository.findById(ord_id)
                .orElseThrow(()-> new IllegalArgumentException("해당 주문번호가 없습니다. ord_id="+ord_id));

        //orderDto 생성
        OrderDto orderDto = new OrderDto(order);

        //orderDto의 prdId로 productDto 조회
        ProductDto productDto = productService.findById(orderDto.getPrdId());

        //조회한 상품정보 orderDto에 입력
        orderDto.setProductDto(productDto);

        return orderDto;
    }

    //userId 로 주문목록 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findByUserId(Long userId){

        List<OrderDto> orderDtoList = orderRepository.findByUserId(userId).stream()
                .map(order -> {return new OrderDto(order);}).collect(Collectors.toList());

        for(OrderDto orderDto : orderDtoList){
            //ordDto의 prdId로 상품정보 조회
            ProductDto productDto = productService.findById(orderDto.getPrdId());
            //조회한 상품정보 orderDto에 입력
            orderDto.setProductDto(productDto);
        }
        return orderDtoList;
    }

    //현재 인증된 사용자의 주문목록 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findByMyUserId(){
        //현재 인증된 사용자의 userId 조회
        UserDto userDto = new UserDto(userService.getMyUserWithAuthorities().get());
        Long userId = userDto.getUserId();
        log.info("userId="+userId);

        List<OrderDto> orderDtoList = orderRepository.findByUserId(userId).stream()
                .map(order -> {return new OrderDto(order);}).collect(Collectors.toList());

        for(OrderDto orderDto : orderDtoList){
            ProductDto productDto = productService.findById(orderDto.getPrdId());
            //조회한 상품정보 orderDto에 입력
            orderDto.setProductDto(productDto);
        }
        return orderDtoList;
    }

}
