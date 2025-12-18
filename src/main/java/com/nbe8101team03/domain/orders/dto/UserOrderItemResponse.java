package com.nbe8101team03.domain.orders.dto;

import com.nbe8101team03.domain.orders.entity.Order;

import java.time.LocalDateTime;

public record UserOrderItemResponse(
        Long orderId,
        Long productId,
        String productName,
        int quantity,
        int totalPrice,
        String status,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate
) {
    public static UserOrderItemResponse from(Order order) {
        return new UserOrderItemResponse(
                order.getOrderId(),
                order.getProduct().getId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus().name(),
                order.getOrderDate(),
                order.getDeliveryDate()
        );
    }
}
