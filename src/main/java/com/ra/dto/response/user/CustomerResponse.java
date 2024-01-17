package com.ra.dto.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {
    private Long id ;
    private String userName ;
    private String email ;
    private String phone ;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate brithDay;
    @Enumerated(EnumType.STRING)
    private String memberLeverName ;
    private Integer scorePoints ;
    private Boolean status;
    private String card;
    private Set<String> roles ;
}
