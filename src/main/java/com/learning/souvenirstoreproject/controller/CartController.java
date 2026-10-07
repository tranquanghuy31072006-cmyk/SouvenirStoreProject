package com.learning.souvenirstoreproject.controller;

import com.learning.souvenirstoreproject.dto.request.AddCartItemRequest;
import com.learning.souvenirstoreproject.dto.request.UpdateCartItemRequest;
import com.learning.souvenirstoreproject.dto.response.ApiResponse;
import com.learning.souvenirstoreproject.dto.response.CartResponse;
import com.learning.souvenirstoreproject.service.CartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;

    @GetMapping("/get-cart")
    public ApiResponse<CartResponse> getCart() {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.getCart())
                .build();
    }

    @PostMapping("/items")
    public ApiResponse<CartResponse> addItemToCart(@Valid @RequestBody AddCartItemRequest request) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.addItemToCart(request))
                .build();
    }

    @PutMapping("/items/{itemId}")
    public ApiResponse<CartResponse> updateItemInCart(@PathVariable Long itemId,
                                                      @Valid @RequestBody UpdateCartItemRequest request) {

        return ApiResponse.<CartResponse>builder()
                .result(cartService.updateItemQuantity(itemId, request))
                .build();

    }

    @DeleteMapping("/items/{itemId}")
    public ApiResponse<CartResponse> deleteItemInCart(@PathVariable Long itemId) {
        return ApiResponse.<CartResponse>builder()
                .result(cartService.removeItem(itemId))
                .build();
    }
}
