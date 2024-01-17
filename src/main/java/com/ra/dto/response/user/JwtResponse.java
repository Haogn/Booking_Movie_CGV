package com.ra.dto.response.user;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String userName;
    private String token ;
    private Set<String> roles;
}
