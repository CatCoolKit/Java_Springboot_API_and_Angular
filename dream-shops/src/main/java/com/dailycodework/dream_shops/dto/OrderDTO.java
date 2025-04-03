package com.dailycodework.dream_shops.dto;

import com.dailycodework.dream_shops.enums.OrderStatus;
import com.dailycodework.dream_shops.model.OrderItem;
import com.dailycodework.dream_shops.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String status;
    private List<OrderItemDTO> items;


}
