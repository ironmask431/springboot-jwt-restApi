package com.leesh.springbootjwttutorial.service;

import com.leesh.springbootjwttutorial.dto.OrderDto;
import com.leesh.springbootjwttutorial.dto.ProductDto;
import com.leesh.springbootjwttutorial.entity.Order;
import com.leesh.springbootjwttutorial.entity.Product;
import com.leesh.springbootjwttutorial.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    //상품목록 전제 조회
    @Transactional(readOnly = true)
    public List<ProductDto> findAll(){
        return productRepository.findAll().stream()
                .map(product-> {return new ProductDto(product);}).collect(Collectors.toList());
    }

    //상품번호로 상품 조회
    @Transactional(readOnly = true)
    public ProductDto findById(Long prdId){
        Product product = productRepository.findById(prdId)
                .orElseThrow(()-> new IllegalArgumentException("해당 상품번호가 없습니다. prdId="+prdId));
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }
}
