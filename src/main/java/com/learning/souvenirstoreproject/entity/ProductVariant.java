package com.learning.souvenirstoreproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "product_variants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    String sku;

    String size;

    String color;

    @Column(name = "stock_quantity")
    Integer stockQuantity;

    @Column(precision = 15, scale = 2)
    BigDecimal price;

    @Column(name = "sale_price", precision = 15, scale = 2)
    BigDecimal salePrice;

    @Column(length = 500)
    String image;

    String status;
}
