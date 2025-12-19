package com.nbe8101team03.domain.orders.controller;

import com.nbe8101team03.domain.orders.dto.OrderResponse;
import com.nbe8101team03.domain.orders.service.OrderService;
import com.nbe8101team03.global.response.CommonResponse;
import com.nbe8101team03.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    //    전체 조회
    @GetMapping("/admin/orders")
    public ResponseEntity<Response<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> res = orderService.getAllOrders();
        return ResponseEntity.ok(CommonResponse.success(res, "주문 목록 조회를 성공하였습니다."));
    }
}
