package com.dailycodework.dream_shops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long itemId;
    private Long quantity;
    private BigDecimal unitPrice;
    private ProductDTO product;
}
