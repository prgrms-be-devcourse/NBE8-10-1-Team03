package com.nbe8101team03.domain.orders.dto;

import com.nbe8101team03.domain.orders.entity.Order;

import java.time.LocalDateTime;

public record OrderResponse(
        Long orderId,
        Long shipmentId,
        String email,
        String address,
        int zipcode,
        Long productId,
        String productName,
        int quantity,
        int totalPrice,
        String status,
        LocalDateTime orderDate,
        LocalDateTime deliveryDate
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getShipmentId(),
                order.getUser().getEmail(),
                order.getUser().getAddress(),
                order.getUser().getZipcode(),
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

