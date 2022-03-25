package com.leesh.springbootjwttutorial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.leesh.springbootjwttutorial.entity.Order;
import com.leesh.springbootjwttutorial.entity.Product;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @NotNull
    private Long ordId;

    @NotNull
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
