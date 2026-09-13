package com.learning.souvenirstoreproject.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    String name;
    String description;
    String slug;
    String status;
}
