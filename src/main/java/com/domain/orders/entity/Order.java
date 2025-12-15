package com.domain.orders.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    //    배송일자
    @Column(nullable = false)
    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    private int quantity;
}
