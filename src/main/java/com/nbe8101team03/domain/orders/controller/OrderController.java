package com.nbe8101team03.domain.orders.controller;

import com.nbe8101team03.domain.orders.dto.CreateOrderRequest;
import com.nbe8101team03.domain.orders.dto.OrderResponse;
import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public OrderResponse createOrder(@RequestBody CreateOrderRequest req) {
        return orderService.createOrder(
                req.email() ,
                req.address() ,
                req.zipcode(),
                req.productId(),
                req.quantity()

        );
    }
    
//    주문 단건 조회
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return orderService.getOrder(orderId);
    }

//    유저별 조회
    @GetMapping("/user")
    public List<OrderResponse> getOrdersByEmail(@RequestParam String email) {
        return orderService.getOrdersByEmail(email);
    }

//    전체 조회
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }
    
//    주문 삭제
    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }


}
