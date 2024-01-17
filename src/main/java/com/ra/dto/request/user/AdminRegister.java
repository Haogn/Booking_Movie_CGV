package com.ra.dto.request.user;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AdminRegister {
    private String userName ;
    private String phone ;
    private String email ;
    private String password ;
    private String  roleName = "admin";
    private Boolean status = true ;
}
