package com.learning.souvenirstoreproject.dto.response;

import com.learning.souvenirstoreproject.enums.PaymentMethod;
import com.learning.souvenirstoreproject.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {

    Long id;

    PaymentMethod paymentMethod;

    String transactionCode;

    BigDecimal amount;

    PaymentStatus paymentStatus;

    LocalDateTime paidAt;
}