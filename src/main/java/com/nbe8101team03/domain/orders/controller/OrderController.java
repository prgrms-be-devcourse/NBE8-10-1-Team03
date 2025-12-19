package com.nbe8101team03.domain.orders.controller;

import com.nbe8101team03.domain.orders.dto.CreateOrderRequest;
import com.nbe8101team03.domain.orders.dto.CreateOrderResponse;
import com.nbe8101team03.domain.orders.dto.OrderResponse;
import com.nbe8101team03.domain.orders.dto.UserOrdersResponse;
import com.nbe8101team03.domain.orders.service.OrderService;
import com.nbe8101team03.global.response.CommonResponse;
import com.nbe8101team03.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Response<CreateOrderResponse>> createOrder(@RequestBody CreateOrderRequest req) {

        CreateOrderResponse res = orderService.createOrder(req);

        return ResponseEntity.status(201)
                .body(CommonResponse.success(res, "주문 생성을 성공하였습니다."));
    }
    
//    주문 단건 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<Response<OrderResponse>> getOrder(@PathVariable Long orderId) {
        OrderResponse res = orderService.getOrder(orderId);
        return ResponseEntity.ok(CommonResponse.success(res, "주문 단건 조회를 성공하였습니다."));
    }

//    유저별 조회
    @GetMapping("/user")
    public ResponseEntity<Response<UserOrdersResponse>> getOrdersByEmail(@RequestParam String email) {
        UserOrdersResponse res = orderService.getOrdersByEmail(email);
        return ResponseEntity.ok(CommonResponse.success(res, "주문 목록 조회를 성공하였습니다."));
    }

//    전체 조회
    @GetMapping
    public ResponseEntity<Response<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> res = orderService.getAllOrders();
        return ResponseEntity.ok(CommonResponse.success(res, "주문 목록 조회를 성공하였습니다."));
    }
    
//    주문 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Response<Void>> deleteOrder(@PathVariable Long orderId) {
      orderService.deleteOrder(orderId);
       return ResponseEntity.ok(CommonResponse.success(null, orderId + "번 주문을 삭제하였습니다."));
    }


}
