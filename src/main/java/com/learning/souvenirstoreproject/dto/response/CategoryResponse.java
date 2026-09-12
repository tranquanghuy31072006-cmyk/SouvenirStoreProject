package com.learning.souvenirstoreproject.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    Integer id;
    String name;
    String description;
    String slug;
    String status;
}
