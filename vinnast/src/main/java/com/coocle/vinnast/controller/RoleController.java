package com.coocle.vinnast.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.coocle.vinnast.Response.ApiResponse;
import com.coocle.vinnast.Response.RoleResponse;
import com.coocle.vinnast.Services.RoleService;
import com.coocle.vinnast.request.RoleRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> deleteRole(@PathVariable String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
