package com.ra.dto.response.timeSlot;

import lombok.*;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TimeSlotResponse {
    private Long id ;
    private String movieName ;
    private String roomName ;
    private LocalTime startTime;
}
