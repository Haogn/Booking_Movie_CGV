package com.ra.security.user_principel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserPrincipal implements UserDetails {
    private Long id ;
    private String userName;
    private String email ;
    private String phone ;
    @JsonIgnore
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate brithDay;
    @Enumerated(EnumType.STRING)
    private String memberLeverName ;
    private Integer scorePoints ;
    private Boolean status;
    private String avatar ;

    private Collection<? extends GrantedAuthority> authorities ;

    public static UserDetails build(Users users) {
        UserPrincipal userPrincipal =UserPrincipal.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .email(users.getEmail())
                .phone(users.getPhone())
                .password(users.getPassword())
                .brithDay(users.getBirthday())
                .memberLeverName(users.getMemberLeverName().name())
                .scorePoints(users.getScorePoints())
                .status(users.getStatus())
                .avatar(users.getAvatar())
                .authorities(users.getRoles().stream().map(item -> new SimpleGrantedAuthority(item.getRoleName().name())).collect(Collectors.toList()))
                .build();
        return userPrincipal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
