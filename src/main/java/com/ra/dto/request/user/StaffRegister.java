package com.ra.dto.request.user;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StaffRegister {
    private String userName ;
    private String email ;
    private String phone ;
    private String password ;
    private String roleName = "staff" ;
    private Long theaterId ;
    private Boolean status ;
}
