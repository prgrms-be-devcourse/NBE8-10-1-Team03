package com.nbe8101team03.domain.orders.dto;

import java.util.List;

public record CreateOrderResponse(
        Long shipmentId,
        List<OrderResponse> orders
) {}