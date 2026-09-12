package com.learning.souvenirstoreproject.dto.request;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequest {
    @NonNull
    Long productId;

    @Min(1)
    @NonNull
    Long stockQuantity;
}
