package com.ra.dto.response.genre;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GenreResponse {
    private Long id ;
    private String genreName ;
    private Boolean isDeleted ;
}
