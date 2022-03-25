package com.leesh.springbootjwttutorial.controller;

import com.leesh.springbootjwttutorial.dto.ProductDto;
import com.leesh.springbootjwttutorial.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    //상품 조회
    @GetMapping("/all")
    public List<ProductDto> findAll(){
        return productService.findAll();
    }

    //상품번호로 주문 조회
    @GetMapping("/prdId/{prdId}")
    public ProductDto findById(@PathVariable Long prdId){
        return productService.findById(prdId);
    }
}
