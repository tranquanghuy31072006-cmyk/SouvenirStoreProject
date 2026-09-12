package com.learning.souvenirstoreproject.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long productId;
    String productName;
    BigDecimal price;
    Integer quantity;
}
