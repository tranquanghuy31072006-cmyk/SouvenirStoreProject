package com.learning.souvenirstoreproject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.learning.souvenirstoreproject.enums.OrderStatus;
import com.learning.souvenirstoreproject.enums.PaymentMethod;
import com.learning.souvenirstoreproject.enums.PaymentStatus;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    Long id;

    String orderCode;

    String receiverName;

    String receiverPhone;

    String receiverAddress;

    BigDecimal totalAmount;

    BigDecimal shippingFee;

    BigDecimal discountAmount;

    BigDecimal finalAmount;

    PaymentStatus paymentStatus;

    PaymentMethod paymentMethod;

    OrderStatus orderStatus;

    String note;

    LocalDateTime createdAt;

    List<OrderItemResponse> items;

    PaymentResponse payment;
}
