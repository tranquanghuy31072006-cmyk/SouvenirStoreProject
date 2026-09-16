package com.learning.souvenirstoreproject.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {

    Long id;

    String username;

    String fullName;

    LocalDate dateOfBirth;

    String email;

    String phone;

    String status;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;
}