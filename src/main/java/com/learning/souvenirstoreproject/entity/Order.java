package com.learning.souvenirstoreproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "order_code", length = 100)
    String orderCode;

    @Column(name = "receiver_name", length = 255)
    String receiverName;

    @Column(name = "receiver_phone", length = 20)
    String receiverPhone;

    @Column(name = "receiver_address", columnDefinition = "TEXT")
    String receiverAddress;

    @Column(name = "total_amount", precision = 15, scale = 2)
    BigDecimal totalAmount;

    @Column(name = "shipping_fee", precision = 15, scale = 2)
    BigDecimal shippingFee;

    @Column(name = "discount_amount", precision = 15, scale = 2)
    BigDecimal discountAmount;

    @Column(name = "final_amount", precision = 15, scale = 2)
    BigDecimal finalAmount;

    @Column(name = "payment_method", length = 50)
    String paymentMethod;

    @Column(name = "payment_status", length = 50)
    String paymentStatus; // PENDING, CONFIRMED, SHIPPING, COMPLETED, CANCELLED

    @Column(name = "order_status", length = 50)
    String orderStatus;

    @Column(columnDefinition = "TEXT")
    String note;

    @Column(name = "created_at")
    LocalDate createdAt;
}
