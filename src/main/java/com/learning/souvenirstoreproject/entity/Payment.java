package com.learning.souvenirstoreproject.entity;

import com.learning.souvenirstoreproject.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 50)
    PaymentMethod paymentMethod;

    @Column(name = "transaction_code", length = 255)
    String transactionCode;

    @Column(name = "amount", precision = 15, scale = 2)
    BigDecimal amount;

    String status;

    @Column(name = "paid_at")
    LocalDateTime paidAt;
}
