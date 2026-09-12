package com.learning.souvenirstoreproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {
    @Id
    @Column(length = 50, nullable = false)
    String name;

    String description;

    @ManyToMany
    Set<Permission> permissions;
}
