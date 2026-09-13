package com.learning.souvenirstoreproject.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Column(unique = true)
    String slug;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Category parentCategory;

    String status; // ACTIVE, INACTIVE

    String description;
}
