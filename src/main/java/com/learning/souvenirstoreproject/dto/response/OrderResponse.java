package com.learning.souvenirstoreproject.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    String orderCode;
    String receiverName;
    String receiverPhone;
    String receiverAddress;
    String note;
    BigDecimal totalAmount;
    LocalDateTime createdAt;
    List<OrderItemResponse> items;
}
