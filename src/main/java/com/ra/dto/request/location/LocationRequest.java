package com.ra.dto.request.location;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class LocationRequest {
    @NotBlank(message = "Location is not blank")
    @NotEmpty(message = "Location is not empty")
    private String locationName ;
    private Boolean isDelete ;
}
