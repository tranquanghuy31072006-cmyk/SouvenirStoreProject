package com.learning.souvenirstoreproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrandUpdateRequest {

    @NotBlank
    @Size(max = 255)
    private String name;

    @Size(max = 500)
    private String logo;
}
