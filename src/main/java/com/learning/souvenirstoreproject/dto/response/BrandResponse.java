package com.learning.souvenirstoreproject.dto.response;

import com.learning.souvenirstoreproject.enums.BrandStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandResponse {
    Long id;

    String name;

    String logo;

    BrandStatus brandStatus;
}
