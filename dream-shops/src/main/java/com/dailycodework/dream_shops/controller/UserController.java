package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.UserDTO;
import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Order;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.request.CreateUserRequest;
import com.dailycodework.dream_shops.request.UserUpdateRequest;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/users")
public class UserController {
    private final IUserService iUserService;

    @GetMapping("/{userId}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        try {
            User user = iUserService.getUserById(userId);
            UserDTO userDTO = iUserService.convertUserToDTO(user);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(userDTO)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message("Error Occurred!")
                            .data(e.getMessage())
                            .build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try {
            User user = iUserService.createUser(request);
            UserDTO userDTO = iUserService.convertUserToDTO(user);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Create User Success!")
                    .data(userDTO)
                    .build());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ApiResponse.builder()
                            .message("Error Occurred!")
                            .data(e.getMessage())
                            .build());
        }
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserUpdateRequest request,
                                                  @PathVariable Long userId){
        try {
            User user = iUserService.updateUser(request, userId);
            UserDTO userDTO = iUserService.convertUserToDTO(user);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(userDTO)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.builder()
                            .message("Error Occurred!")
                            .data(e.getMessage())
                            .build());
        }
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try {
            iUserService.deleteUser(userId);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(null)
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
