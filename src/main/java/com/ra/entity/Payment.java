package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String paymentMethod;
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
    public String createUser;
    public String updateUser;
    private Boolean isDelete;
}
