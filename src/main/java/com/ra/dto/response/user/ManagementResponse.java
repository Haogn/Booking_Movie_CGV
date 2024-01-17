package com.ra.dto.response.user;

import lombok.*;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ManagementResponse {
    private Long id ;
    private String userName ;
    private String phone ;
    private String email ;
    private Set<String> roles ;
    private Boolean status ;
}
