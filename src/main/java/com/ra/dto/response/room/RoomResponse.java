package com.ra.dto.response.room;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomResponse {
    private Long id ;
    private String roomName ;
    private Integer numberOfSeatsInARow ;
    private Integer numberOfSeatsInAColumn ;
    private String roomType ;
    private String theaterName ;
    private Boolean isDeleted;
}
