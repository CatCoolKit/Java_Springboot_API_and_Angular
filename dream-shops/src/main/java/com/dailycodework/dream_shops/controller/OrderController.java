package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.OrderDTO;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Order;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService iOrderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = iOrderService.placeOrder(userId);
            OrderDTO orderDTO = iOrderService.convertToDTO(order);
            return ResponseEntity.ok(ApiResponse.builder()
                            .message("Item Order Success!")
                            .data(orderDTO)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.builder()
                            .message("Error Occurred!")
                            .data(e.getMessage())
                            .build());
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(
            @PathVariable Long orderId
    ){
        try {
            OrderDTO order = iOrderService.getOrder(orderId);
            return ResponseEntity.ok(ApiResponse.builder()
                            .message("Item Order Success!")
                            .data(order)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message("Error Occurred!")
                            .data(e.getMessage())
                            .build());
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrders(
            @PathVariable Long userId
    ){
        try {
            List<OrderDTO> orders = iOrderService.getUserOrders(userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Item Order Success!")
                    .data(orders)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message("Error Occurred!")
                            .data(e.getMessage())
                            .build());
        }
    }
}
