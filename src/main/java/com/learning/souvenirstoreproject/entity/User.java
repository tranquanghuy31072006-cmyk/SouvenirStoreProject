package com.learning.souvenirstoreproject.entity;

import com.learning.souvenirstoreproject.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100, unique = true, nullable = false)
    String username;

    LocalDate dateOfBirth;

    @Column(nullable = false)
    String password;

    @Column(unique = true, nullable = false)
    String email;

    @Column(length = 20, unique = true, nullable = false)
    String phone;

    @Column(name = "full_name")
    String fullName;

    String avatar;

    @ManyToOne
    @JoinColumn(name = "role_name",nullable = false)
    Role role;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    UserStatus status = UserStatus.ACTIVE;

    @Column(name = "created_at")
    LocalDateTime createdAt;
}
