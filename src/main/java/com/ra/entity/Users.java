package com.ra.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ra.constant.MemberLeverName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String userName;
    private String email ;
    private String phone ;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
    @Enumerated(EnumType.STRING)
    private MemberLeverName memberLeverName ;
    private Integer scorePoints ;
    private String card;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_detail",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles ;
    private String avatar ;
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
    public String createUser;
    public String updateUser;
    private Boolean status;
    private Long localTheater ;
}
