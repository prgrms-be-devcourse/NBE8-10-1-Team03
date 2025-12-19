package com.nbe8101team03.domain.orders.service;

import com.nbe8101team03.domain.orders.dto.CreateOrderRequest;
import com.nbe8101team03.domain.orders.dto.OrderResponse;
import com.nbe8101team03.domain.orders.dto.UserOrderItemResponse;
import com.nbe8101team03.domain.orders.dto.UserOrdersResponse;
import com.nbe8101team03.domain.orders.dto.*;
import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.entity.OrderStatus;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
import com.nbe8101team03.domain.product.entity.Product;
import com.nbe8101team03.domain.product.repository.ProductRepository;
import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.repository.UserRepository;
import com.nbe8101team03.global.exception.errorCode.OrderErrorCode;
import com.nbe8101team03.global.exception.errorCode.ProductErrorCode;
import com.nbe8101team03.global.exception.exception.OrderException;
import com.nbe8101team03.global.exception.exception.ProductException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;



//    주문하기
@Transactional
public CreateOrderResponse  createOrder(CreateOrderRequest dto) {

//       유저생성
    User user = userRepository.findByEmail(dto.email())
            .orElseGet(() -> userRepository.save(
                    User.builder()
                            .email(dto.email())
                            .address(dto.address())
                            .zipcode(dto.zipcode())
                            .build()
            ));

//        details 검증


//        수량 검증
    if (dto.details() == null || dto.details().isEmpty()) {
        throw new OrderException(OrderErrorCode.INVALID_QUANTITY);
    }
    if (dto.details().stream().anyMatch(d -> d.quantity() <= 0)) {
        throw new OrderException(OrderErrorCode.INVALID_QUANTITY);
    }
//        같은 상품이 여러번 들어오면 수량 합치기
    Map<Long, Integer> quantityMap = dto.details().stream()
            .collect(Collectors.toMap(
                    CreateOrderRequest.OrderDetail::productId,
                    CreateOrderRequest.OrderDetail::quantity,
                    Integer::sum
            ));

    //        상품을 한번에 조회
    Set<Long> productIds = quantityMap.keySet();
    List<Product> products = productRepository.findAllById(productIds);

//      없는 상품 섞였는지 체크
    if (products.size() != productIds.size()) {
        throw new ProductException(
                ProductErrorCode.UNKNOWN_PRODUCT,
                "요청에 존재하지 않는 상품이 포함되어 있습니다."
        );
    }

//      오늘 오늘 READY 주문 기준 shipmentId 결정
    LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
    LocalDateTime endOfToday = LocalDate.now().atTime(23, 59, 59);

    Long shipmentId = orderRepository
            .findFirstByUserAndStatusAndOrderDateBetween(
                    user,
                    OrderStatus.READY,
                    startOfToday,
                    endOfToday
            )
            .map(Order::getShipmentId)
            .orElseGet(() -> System.currentTimeMillis());

    List<Order> newOrders = new ArrayList<>();
    List<OrderResponse> result = new ArrayList<>();
    for (Product product : products) {
        int qty = quantityMap.get(product.getId());

        Optional<Order> existingOrder =
                orderRepository.findByUserAndProductAndShipmentIdAndStatus(
                        user,
                        product,
                        shipmentId,
                        OrderStatus.READY
                );

        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.addQuantity(qty);
            result.add(OrderResponse.from(order));
            continue;
        }

        Order order = Order.builder()
                .shipmentId(shipmentId)
                .user(user)
                .product(product)
                .quantity(qty)
                .totalPrice(product.getCost() * qty)
                .status(OrderStatus.READY)
                .build();

        newOrders.add(order);
    }

    // 새로 만든 것만 저장
    if (!newOrders.isEmpty()) {
        List<Order> saved = orderRepository.saveAll(newOrders);
        saved.forEach(o -> result.add(OrderResponse.from(o)));
    }

    return new CreateOrderResponse(shipmentId, result);

}

    //    주문 조회
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new OrderException(OrderErrorCode.UNKNOWN_ORDER));

        return OrderResponse.from(order);
    }

//    유저별 주문 조회
@Transactional(readOnly = true)
public UserOrdersResponse getOrdersByEmail(String email) {
    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new OrderException(OrderErrorCode.UNKNOWN_USER));

    List<UserOrderItemResponse> orders =
            orderRepository.findAllByUserOrderByOrderDateAsc(user)
                    .stream()
                    .map(UserOrderItemResponse::from)
                    .toList();

    return new UserOrdersResponse(
            user.getEmail(),
            user.getAddress(),
            user.getZipcode(),
            orders
    );
}


//    관리자용 전체 조회
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll().stream()
                .map(OrderResponse::from)
                .toList();
    }


    //    주문 삭제
    public void deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderException(OrderErrorCode.UNKNOWN_ORDER));

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new OrderException(OrderErrorCode.ALREADY_CANCELED);
        }

        orderRepository.delete(order);



    }
}
