package com.nbe8101team03.domain.orders.dto;

public record CreateOrderRequest(String email, String address, int zipcode, Long productId,  int quantity) {}
