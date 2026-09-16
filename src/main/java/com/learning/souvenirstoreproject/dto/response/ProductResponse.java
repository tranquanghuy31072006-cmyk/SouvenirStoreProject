package com.learning.souvenirstoreproject.dto.response;

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
public class ProductResponse {

    Long id;

    String name;

    String description;

    BigDecimal price;

    Integer stockQuantity;

    String imageUrl;

    String status;

    Long categoryId;

    String categoryName;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}
