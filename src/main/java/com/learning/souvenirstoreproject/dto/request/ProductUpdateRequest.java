package com.learning.souvenirstoreproject.dto.request;

import com.learning.souvenirstoreproject.enums.ProductStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {
    String name;

    String description;

    @Positive
    @NotNull
    private BigDecimal price;

    @Min(0)
    private Integer stockQuantity;

    @NotNull
    private Long categoryId;

    String imageUrl;
}
