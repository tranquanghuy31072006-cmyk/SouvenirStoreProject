package com.learning.souvenirstoreproject.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    @NotBlank
    @Size(max = 150)
    private String name;

    @Positive
    @NotNull
    private BigDecimal price;

    private String status;

    @Min(0)
    private Integer stockQuantity;

    @NonNull
    private Long categoryId;
}
