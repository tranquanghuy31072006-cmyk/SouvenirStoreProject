package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.ProductRequest;
import com.learning.souvenirstoreproject.exception.ApiResponse;
import com.learning.souvenirstoreproject.dto.response.ProductResponse;
import com.learning.souvenirstoreproject.service.ProductService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    //createProduct
    @PostMapping("/create-product")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.createProduct(productRequest))
                .build();
    }

    //getProductById
    @GetMapping("/get-product-by-id/{productId}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.getProductById(productId))
                .build();
    }

    //update
    @PutMapping("/update-product/{productId}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductRequest productRequest) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.updateProduct(productId, productRequest))
                .build();
    }

    //getAllProducts
    @GetMapping("/get-all-products")
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.<List<ProductResponse>>builder()
                .result(productService.getAllProducts())
                .build();
    }

    @PutMapping("/activate-product-status/{productId}")
    public ApiResponse<ProductResponse> activateProductStatus(@PathVariable Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.activateProductStatus(productId))
                .build();
    }

    //deactivateProductStatus
    @PutMapping("/deactivate-product-status/{productId}")
    public ApiResponse<ProductResponse> deactivateProductStatus(@PathVariable Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .result(productService.deactivateProductStatus(productId))
                .build();
    }

    //delete
    @DeleteMapping("/delete-product/{productId}")
    public ApiResponse<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);

        return ApiResponse.<String>builder()
                .message("Product deleted successfully")
                .build();
    }
}
