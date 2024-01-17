package com.ra.entity;

import com.ra.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "BOOKING_MOVIE")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;
    private Double total;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    public LocalDateTime createTime;
    public LocalDateTime updateTime;
    public String createUser;
    public String updateUser;
}
