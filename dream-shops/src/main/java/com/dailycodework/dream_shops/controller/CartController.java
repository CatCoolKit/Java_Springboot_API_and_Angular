package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final ICartService iCartService;

    @GetMapping("/{cartId}/my-cart")
    public ResponseEntity<ApiResponse> getCart(
            @PathVariable Long cartId
    ){
        try {
            Cart cart = iCartService.getCart(cartId);
            return ResponseEntity.ok(ApiResponse.builder()
                            .message("Success")
                            .data(cart)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(
            @PathVariable Long cartId
    ){
        try {
            iCartService.clearCart(cartId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Clear Cart Success")
                    .data(null)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

    @GetMapping("/{cartId}/cart/total-price")
    public ResponseEntity<ApiResponse> getToTalAmount(
            @PathVariable Long cartId
    ){
        try {
            BigDecimal totalPrice = iCartService.getTotalPrice(cartId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Total Price")
                    .data(totalPrice)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        }
    }

}
