package com.dailycodework.dream_shops.service.order;

import com.dailycodework.dream_shops.dto.OrderDTO;
import com.dailycodework.dream_shops.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDTO getOrder(Long orderId);

    List<OrderDTO> getUserOrders(Long userId);

    OrderDTO convertToDTO(Order order);
}
