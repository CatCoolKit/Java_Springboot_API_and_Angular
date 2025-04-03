package com.coocle.vinnast.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.coocle.vinnast.DTO.UserDTO;
import com.coocle.vinnast.Response.ApiResponse;
import com.coocle.vinnast.Response.UserResponse;
import com.coocle.vinnast.Services.UserService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    UserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("Controller: create user");
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        UserResponse user = userService.createUser(userDTO);
        return ResponseEntity.ok(ApiResponse.builder()
                .code(apiResponse.getCode())
                .message("Create new user successfully")
                .result(user)
                .build());
    }

    @GetMapping()
    public ApiResponse<List<UserResponse>> getAllUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    public UserResponse updateUserById(@PathVariable("userId") String userId, @RequestBody UserDTO userDTO) {
        return userService.updateUserById(userId, userDTO);
    }

    @DeleteMapping("/{userId}")
    public String deleteUserById(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "User has been deleted";
    }
}
