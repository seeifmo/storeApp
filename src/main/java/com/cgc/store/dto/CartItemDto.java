package com.cgc.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDto {
    private Long id;
    private Long productId;
    private Long userId;
    private Integer quantity;
}
