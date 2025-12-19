package com.nbe8101team03.domain.orders.dto;

import java.util.List;

public record CreateOrderRequest(
        String email,
        String address,
        int zipcode,
        List<OrderDetail> details
) {
    public record OrderDetail(
            Long productId,
            int quantity
    ) {}
}