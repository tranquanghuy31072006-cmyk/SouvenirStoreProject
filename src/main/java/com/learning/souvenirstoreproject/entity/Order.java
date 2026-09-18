package com.learning.souvenirstoreproject.entity;

import com.learning.souvenirstoreproject.enums.OrderStatus;
import com.learning.souvenirstoreproject.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", length = 50)
    PaymentMethod paymentMethod;

    @Column(name = "payment_status", length = 50)
    String paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", length = 50)
    OrderStatus orderStatus;

    @Column(columnDefinition = "TEXT")
    String note;

    @CreationTimestamp
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Builder.Default
    @OneToMany
    List<OrderItem> orderItems = new ArrayList<>();
}