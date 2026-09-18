package com.learning.souvenirstoreproject.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    private String description;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    private Long categoryId;

    private String thumbnail;

    private List<ProductImageUpdateRequest> imageUrls;
}