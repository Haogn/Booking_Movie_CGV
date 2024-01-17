package com.ra.dto.response.chair;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChairResponse {
    private Long id ;
    private String chairName ;
    private String chairType ;
    private String roomName  ;
    private Boolean isDeleted;
}
