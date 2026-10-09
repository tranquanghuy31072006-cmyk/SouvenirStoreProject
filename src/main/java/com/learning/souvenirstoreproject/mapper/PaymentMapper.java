package com.learning.souvenirstoreproject.mapper;

import com.learning.souvenirstoreproject.dto.response.PaymentResponse;
import com.learning.souvenirstoreproject.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toPaymentResponse(Payment payment);
}
