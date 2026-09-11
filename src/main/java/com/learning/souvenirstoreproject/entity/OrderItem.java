package com.learning.souvenirstoreproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    ProductVariant productVariant;

    @Column(name = "product_name", length = 255)
    String productName;

    @Column(name = "product_image", length = 500)
    String productImage;

    String size;

    String color;

    Integer quantity;

    @Column(precision = 15, scale = 2)
    BigDecimal price;

    @Column(name = "total_price", precision = 15, scale = 2)
    BigDecimal totalPrice;
}
