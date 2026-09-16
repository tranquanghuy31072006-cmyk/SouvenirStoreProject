package com.learning.souvenirstoreproject.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPasswordUpdateRequest {
    String oldPassword;
    String newPassword;
}
