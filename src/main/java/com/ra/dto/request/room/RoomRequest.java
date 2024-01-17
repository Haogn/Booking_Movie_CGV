package com.ra.dto.request.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomRequest {
    @NotEmpty(message = "roomName is not empty")
    @NotBlank(message = "roomName is not blank")
    private String roomName ;
    @NotNull(message = "numberOfSeatsInARow is not null")
    private Integer numberOfSeatsInARow ;
    @NotNull(message = "numberOfSeatsInAColumn is not null")
    private Integer numberOfSeatsInAColumn ;
    @NotEmpty(message = "roomType is not empty")
    @NotBlank(message = "roomType is not blank")
    private String roomType ;
    @NotNull(message = "theaterId is not null")
    private Long theaterId ;
    private Boolean isDeleted;
}
