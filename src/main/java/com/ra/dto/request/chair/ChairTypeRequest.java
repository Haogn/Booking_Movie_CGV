package com.ra.dto.request.chair;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairTypeRequest {
    @NotEmpty(message = "chairType is not empty")
    @NotBlank(message = "chairType is not blank")
    private String chairType ;
}
