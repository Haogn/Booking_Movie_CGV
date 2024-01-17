package com.ra.mapper;

import com.ra.dto.response.user.CustomerResponse;
import com.ra.dto.response.user.ManagementResponse;
import com.ra.dto.response.user.StaffResponse;
import com.ra.entity.Users;
import com.ra.repository.IRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class UserMapper {
    private IRoleRepository roleRepository ;
    public CustomerResponse toCusResponse(Users users) {
        return CustomerResponse.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .email(users.getEmail())
                .phone(users.getPhone())
                .brithDay(users.getBirthday())
                .memberLeverName(users.getMemberLeverName().name())
                .scorePoints(users.getScorePoints())
                .status(users.getStatus())
                .card(users.getCard())
                .roles(users.getRoles().stream().map(item -> item.getRoleName().name()).collect(Collectors.toSet()))
                .build();
    }

    public ManagementResponse toManaResponse(Users users) {
        return ManagementResponse.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .phone(users.getPhone())
                .email(users.getEmail())
                .status(users.getStatus())
                .roles(users.getRoles().stream().map(item -> item.getRoleName().name()).collect(Collectors.toSet()))
                .build();
    }

    public StaffResponse toStaffResponse(Users users) {
        return StaffResponse.builder()
                .id(users.getId())
                .userName(users.getUserName())
                .phone(users.getPhone())
                .email(users.getEmail())
                .status(users.getStatus())
                .roles(users.getRoles().stream().map(item -> item.getRoleName().name()).collect(Collectors.toSet()))
                .build();
    }



}
