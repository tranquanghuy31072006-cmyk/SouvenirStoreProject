package com.learning.souvenirstoreproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Permission {

    @Id
    @Column(length = 100, nullable = false)
    String name;

    @Column(length = 255)
    String description;
}
