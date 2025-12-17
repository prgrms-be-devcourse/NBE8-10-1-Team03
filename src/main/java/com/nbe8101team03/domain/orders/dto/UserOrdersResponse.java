package com.nbe8101team03.domain.orders.dto;

import java.util.List;

public record UserOrdersResponse(
        String email,
        String address,
        int zipcode,
        List<OrderResponse> orders
) {

}
