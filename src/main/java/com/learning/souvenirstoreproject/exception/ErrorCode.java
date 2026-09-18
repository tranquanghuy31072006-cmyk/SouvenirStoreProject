package com.learning.souvenirstoreproject.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
    EMAIL_EXISTED(1000, "Email not exists"),
    USER_NOT_FOUND(1001, "User not found"),
    CATEGORY_NOT_FOUND(1002, "Category not found"),
    PRODUCT_NOT_FOUND(1003, "Product not found"),
    PRODUCT_INACTIVE(1004, "Product inactive"),
    OUT_OF_STOCK(1005, "Out of stock"),
    INSUFFICIENT_STOCK(1006, "Insufficient stock"),
    CART_ITEM_NOT_FOUND(1007, "Cart item not found"),
    ORDER_NOT_FOUND(1008, "Order not found"),
    INVALID_ORDER_STATUS(1009, "Invalid order status"),
    VALIDATION_FAILED(1010, "Validation fail"),
    UNAUTHENTICATED(1011, "UNAUTHENTICATED"),
    ACCESS_DENIED(1012, "Access denied"),
    UNCATEGORIZED(9999, "Uncategorized"),
    USER_EXISTED(1013, "User existed"),
    CATEGORY_EXISTED(1014, "category existed"),
    PRODUCT_EXISTED(1015, "product existed"),
    PASSWORD_INCORRECT(1016, "Password incorrect"),
    ROLE_NOT_FOUND(1017, "Role not found"),
    CATEGORY_HAS_PRODUCTS(1018, "Category has products"),
    BRAND_EXISTED(1019, "Brand not found"),
    BRAND_NOT_FOUND(1020, "Brand not found"),
    ;

    int code;
    String message;
}
