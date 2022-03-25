package com.leesh.springbootjwttutorial.dto;


import com.leesh.springbootjwttutorial.entity.Product;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long prdId;

    @NotNull
    @Size(max=100)
    private String prdNm;

    @NotNull
    private int price;

    public ProductDto(Product entity){
        this.prdId = entity.getPrdId();
        this.prdNm = entity.getPrdNm();
        this.price = entity.getPrice();
    }
}
