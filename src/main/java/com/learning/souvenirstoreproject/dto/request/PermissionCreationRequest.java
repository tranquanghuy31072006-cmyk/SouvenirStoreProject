package com.learning.souvenirstoreproject.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PermissionCreationRequest {
    @NotBlank(message = "Permission name must not be empty")
    @Size(min = 3, max = 100, message = "The permission name must be " +
            "at least 3 characters long and must not exceed 100 characters.")
    String name;

    @NotBlank
    @Size(max = 250, message = "The permission name must not exceed 250 characters.")
    String description;
}
