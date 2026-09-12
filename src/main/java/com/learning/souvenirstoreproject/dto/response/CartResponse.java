package com.learning.souvenirstoreproject.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Long cartItemId;
    Long itemTotal;
    Long itemAmount;
    List<CartItemResponse> items;
}
