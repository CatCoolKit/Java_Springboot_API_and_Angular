package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.cart.ICartItemService;
import com.dailycodework.dream_shops.service.cart.ICartService;
import com.dailycodework.dream_shops.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService iCartItemService;
    private final ICartService iCartService;
    private final IUserService iUserService;

    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity
    ){
        try {
            User user = iUserService.getAuthenticatedUser();
            Cart newCart = iCartService.initializeNewCart(user);

            iCartItemService.addItemToCart(newCart.getId(), productId, quantity);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Add Item Success")
                    .data(null)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                            .build());
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                    .build());
        }
    }

    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long itemId
    ){
        try {
            iCartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Remove Item Success")
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

    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam Integer quantity
    ){
        try {
            iCartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Update Item Success")
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
}
