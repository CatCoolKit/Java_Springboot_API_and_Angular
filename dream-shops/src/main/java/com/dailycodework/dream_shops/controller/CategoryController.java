package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService iCategoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = iCategoryService.getAllCategories();
            return ResponseEntity.ok(ApiResponse.builder()
                            .message("Found!")
                            .data(categories)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message("Error!")
                    .data(INTERNAL_SERVER_ERROR)
                    .build());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(
            @RequestBody Category name
    ){
        try {
            Category category = iCategoryService.addCategory(name);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(category)
                    .build());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(
            @PathVariable Long id
    ){
        try {
            Category category = iCategoryService.getCategoryById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Found!")
                    .data(category)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(
            @PathVariable String name
    ){
        try {
            Category category = iCategoryService.getCategoryByName(name);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Found!")
                    .data(category)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id
    ){
        try {
            iCategoryService.deleteCategoryById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Delete success!")
                    .data(null)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody Category category
    ){
        try {
            iCategoryService.updateCategory(category, id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Update success!")
                    .data(null)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

}
