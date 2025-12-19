package com.nbe8101team03.domain.orders.entity;

import com.nbe8101team03.domain.product.entity.Product;
import com.nbe8101team03.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Column(nullable = false)
    private Long shipmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

//    주문일자
//    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    //    배송일자
    @Column(nullable = false, updatable = false)
    private LocalDateTime deliveryDate;

    @PrePersist
    private void initDates() {
        this.orderDate = LocalDateTime.now();
        this.deliveryDate = calculateDeliveryDate(this.orderDate);
    }

    private LocalDateTime calculateDeliveryDate(LocalDateTime orderDate) {
        LocalDateTime today14 = orderDate.toLocalDate().atTime(14, 0);

        if (orderDate.isBefore(today14)) {
            return today14.plusDays(0);
        }
        return today14.plusDays(1);
    }

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private int totalPrice;

    public void changeStatus(OrderStatus status) {
        this.status = status;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
        this.totalPrice += this.product.getCost() * quantity;
    }

    @Builder
    public Order(Long shipmentId, User user, Product product , OrderStatus status,  int quantity, int totalPrice) {
        this.shipmentId = shipmentId;
        this.user = user;
        this.product = product;
        this.status = status;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }



}
