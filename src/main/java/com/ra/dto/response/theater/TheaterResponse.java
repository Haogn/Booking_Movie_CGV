package com.ra.dto.response.theater;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TheaterResponse {
    private Long id ;
    private String theaterName ;
    private Boolean isDeleted;
    private String locationName ;
}
