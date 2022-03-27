package com.leesh.springbootjwttutorial.service;

import com.leesh.springbootjwttutorial.dto.OrderDto;
import com.leesh.springbootjwttutorial.dto.UserDto;
import com.leesh.springbootjwttutorial.entity.Order;
import com.leesh.springbootjwttutorial.repository.OrderRepository;
import com.leesh.springbootjwttutorial.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
            settingProductDto(orderDto);
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

        settingProductDto(orderDto);

        return orderDto;
    }

    //userId 로 주문목록 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findByUserId(Long userId){

        List<OrderDto> orderDtoList = orderRepository.findByUserId(userId).stream()
                .map(order -> {return new OrderDto(order);}).collect(Collectors.toList());

        for(OrderDto orderDto : orderDtoList){
            settingProductDto(orderDto);
        }
        return orderDtoList;
    }

    //현재 인증된 사용자의 주문목록 조회
    @Transactional(readOnly = true)
    public List<OrderDto> findByMyUserId(){
        //현재 인증된 사용자의 userId 조회
        UserDto userDto = new UserDto(userService.getMyUserWithAuthorities().get());
        Long userId = userDto.getUserId();
        log.info("findByMyUserId - userId="+userId);

        List<OrderDto> orderDtoList = orderRepository.findByUserId(userId).stream()
                .map(order -> {return new OrderDto(order);}).collect(Collectors.toList());

        for(OrderDto orderDto : orderDtoList){
            settingProductDto(orderDto);
        }
        return orderDtoList;
    }

    //주문 입력
    public OrderDto save(OrderDto orderDto){
        //주문 받은 prdId 검증
        productService.findById(orderDto.getPrdId());

        //현재 인증된 사용자의 userId 조회
        UserDto userDto = new UserDto(userService.getMyUserWithAuthorities().get());
        Long userId = userDto.getUserId();
        log.info("save - userId="+userId);

        //Order Entity 생성
        Order order = Order.builder()
                .userId(userId)
                .prdId(orderDto.getPrdId())
                .build();

        order = orderRepository.save(order);

        orderDto = new OrderDto(order);
        settingProductDto(orderDto);

        return orderDto;
    }

    //orderDto의 prdId로 상품정보 조회하여 orderDto 의 productDto 필드에 입력
    public void settingProductDto(OrderDto orderDto){
        orderDto.setProductDto(productService.findById(orderDto.getPrdId()));
    }
}
