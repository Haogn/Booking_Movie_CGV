package com.ra.dto.request.user;

import com.ra.constant.RoleName;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ManagementRegister {
    private String userName ;
    private String email ;
    private String phone ;
    private String password ;
    private String roleName = "customer" ;
    private Boolean status ;
    private Long theaterId ;
}
