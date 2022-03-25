package com.leesh.springbootjwttutorial.dto;

import com.leesh.springbootjwttutorial.entity.Order;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long ordId;

    private Long userId;

    @NotNull
    private Long prdId;

    private ProductDto productDto;

    public OrderDto(Order entity){
        this.ordId = entity.getOrdId();
        this.userId = entity.getUserId();
        this.prdId = entity.getPrdId();
    }
}
