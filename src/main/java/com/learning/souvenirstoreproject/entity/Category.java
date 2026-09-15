package com.learning.souvenirstoreproject.entity;

import com.learning.souvenirstoreproject.enums.CategoryStatus;
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

    @Column(unique = true, nullable = false)
    String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Category parentCategory;

    @Enumerated(EnumType.STRING)
    CategoryStatus status;// ACTIVE, INACTIVE

    String description;
}
