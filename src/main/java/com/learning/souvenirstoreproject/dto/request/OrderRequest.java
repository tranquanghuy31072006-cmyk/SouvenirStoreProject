package com.learning.souvenirstoreproject.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @NotBlank
    @Size(max = 150)
    String receiverName;

    @NotNull
    @Size(max = 20)
    String phone;

    @NotBlank
    @Size(max = 250)
    String address;

    @Size(max = 500)
    String note;
}
