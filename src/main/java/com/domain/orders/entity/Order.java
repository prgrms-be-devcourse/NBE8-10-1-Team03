package com.domain.orders.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
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
    @CreatedDate
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    //    배송일자
    @Column(nullable = false, updatable = false)
    private LocalDateTime deliveryDate;

    @Column(nullable = false)
    private int quantity;

    @Builder
    public Order(Long userId, OrderStatus status, LocalDateTime deliveryDate, int quantity) {
        this.userId = userId;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.quantity = quantity;
    }

}
