package com.learning.souvenirstoreproject.entity;

import com.learning.souvenirstoreproject.enums.BrandStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String name;

    @Column(length = 500)
    String logo;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    BrandStatus status =  BrandStatus.ACTIVE;
}
