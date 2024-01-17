package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoryName;
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
    public String createUser;
    public String updateUser;
    private Boolean isDeleted;
}
