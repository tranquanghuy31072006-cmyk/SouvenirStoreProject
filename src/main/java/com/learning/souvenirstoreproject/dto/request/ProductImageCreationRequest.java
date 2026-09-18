package com.learning.souvenirstoreproject.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageCreationRequest {

    @NotBlank
    private String imageUrl;

    @Min(0)
    private Integer sortOrder;
}
