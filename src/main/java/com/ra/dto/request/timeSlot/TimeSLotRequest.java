package com.ra.dto.request.timeSlot;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSLotRequest {
    @NotNull(message = "movieId is not null")
    private Long movieId ;
    @NotNull(message = "roomId is not null")
    private Long roomId ;
    private LocalTime startTime ;
    private Boolean isDeleted;
}
