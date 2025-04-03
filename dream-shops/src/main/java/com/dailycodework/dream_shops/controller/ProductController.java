package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.dto.ProductDTO;
import com.dailycodework.dream_shops.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService iProductService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = iProductService.getAllProducts();
        List<ProductDTO> productDTOS = iProductService.getConvertedProducts(products);
        return ResponseEntity.ok(ApiResponse.builder()
                        .message("Success!")
                        .data(productDTOS)
                .build());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> getProductById(
            @PathVariable("productId") Long id
    ){
        try {
            Product product = iProductService.getProductById(id);
            ProductDTO productDTO = iProductService.convertToDto(product);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(productDTO)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                            .message(e.getMessage())
                            .data(null)
                    .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(
            @RequestBody AddProductRequest request
    ){
        try {
            Product product = iProductService.addProduct(request);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Add product success!")
                    .data(product)
                    .build());
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(
            @RequestBody ProductUpdateRequest request,
            @PathVariable("productId") Long id
    ){
        try {
            Product product = iProductService.updateProductById(request, id);
            ProductDTO productDTO = iProductService.convertToDto(product);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Update product success!")
                    .data(productDTO)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable("productId") Long id
    ){
        try {
            iProductService.deleteProductById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Delete product success!")
                    .data(null)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(
            @RequestParam String brandName,
            @RequestParam String productName
    ){
        try {
            List<Product> products =  iProductService.getProductsByBrandAndName(brandName, productName);
            List<ProductDTO> productDTOS = iProductService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("No products found")
                        .data(null)
                        .build());
            }
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(productDTOS)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(
            @RequestParam String category,
            @RequestParam String brandName
    ){
        try {
            List<Product> products =  iProductService.getProductsByCategoryAndBrand(category, brandName);
            List<ProductDTO> productDTOS = iProductService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("No products found")
                        .data(null)
                        .build());
            }
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(productDTOS)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/product/by-product-name/{name}")
    public ResponseEntity<ApiResponse> getProductByName(
            @PathVariable String name
    ){
        try {
            List<Product> products = iProductService.getProductsByName(name);
            List<ProductDTO> productDTOS = iProductService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("No products found")
                        .data(null)
                        .build());
            }
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(productDTOS)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/product/by-brand")
    public ResponseEntity<ApiResponse> getProductByBrand(
            @RequestParam String brand
    ){
        try {
            List<Product> products =  iProductService.getProductsByBrand(brand);
            List<ProductDTO> productDTOS = iProductService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("No products found")
                        .data(null)
                        .build());
            }
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(productDTOS)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(OK).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/product/by-category/{category}")
    public ResponseEntity<ApiResponse> getProductByCategory(
            @PathVariable String category
    ){
        try {
            List<Product> products =  iProductService.getProductsByCategory(category);
            List<ProductDTO> productDTOS = iProductService.getConvertedProducts(products);
            if(products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("No products found")
                        .data(null)
                        .build());
            }
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success!")
                    .data(productDTOS)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(OK).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }

    @GetMapping("/product/count/by-brand-and-name")
    public ResponseEntity<ApiResponse> countProductByBrandAndName(
            @RequestParam String brand,
            @RequestParam String name
    ){
        try {
            Long productCount =  iProductService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Product count!")
                    .data(productCount)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(OK).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .data(null)
                    .build());
        }
    }
}
