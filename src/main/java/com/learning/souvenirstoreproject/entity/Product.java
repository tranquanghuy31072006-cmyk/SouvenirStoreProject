package com.learning.souvenirstoreproject.entity;

import com.learning.souvenirstoreproject.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @Column(unique = true, nullable = false)
    String name;

    String thumbnail;

    @Column(precision = 15, scale = 2)
    BigDecimal price;

    @Column(name = "sale_price", precision = 15, scale = 2)
    BigDecimal salePrice;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    ProductStatus status = ProductStatus.ACTIVE;

    @Column(columnDefinition = "TEXT")
    String description;

    @OneToMany
    @Builder.Default
    List<ProductImage> productImages = new ArrayList<>();

    @OneToMany
    @Builder.Default
    List<ProductVariant>  productVariants = new ArrayList<>();
}
