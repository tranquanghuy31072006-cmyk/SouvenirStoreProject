package com.learning.souvenirstoreproject.dto.request;

import com.learning.souvenirstoreproject.enums.CategoryStatus;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryUpdateRequest {
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;
}
