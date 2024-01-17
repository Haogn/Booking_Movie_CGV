package com.ra.dto.response.location;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@Builder
public class LocationResponse {
    private Long id ;
    private String locationName ;
    public LocalDateTime createDateTime;
    public LocalDateTime updateDateTime;
    public Integer createUser;
    public Integer updateUser;
    private Boolean isDelete = false ;
}
