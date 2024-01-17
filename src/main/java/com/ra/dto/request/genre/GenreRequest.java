package com.ra.dto.request.genre;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GenreRequest {
    private String genreName ;
    private Boolean isDeleted ;
}
