package com.nbe8101team03.domain.orders.service;

import com.nbe8101team03.domain.orders.dto.OrderResponse;
import com.nbe8101team03.domain.orders.dto.UserOrderItemResponse;
import com.nbe8101team03.domain.orders.dto.UserOrdersResponse;
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
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


//    주문하기
    @Transactional
    public OrderResponse createOrder(String email, String address, int zipcode, Long productId , int quantity) {

//       유저생성
        User user = userRepository.findByEmail(email)
                .orElseGet(() ->
                        userRepository.save(
                                User.builder()
                                        .email(email)
                                        .address(address)
                                        .zipcode(zipcode)
                                        .build()
                        )
                );


//        수량 검증
        if (quantity <= 0) {
            throw new OrderException(OrderErrorCode.INVALID_QUANTITY);
        }

//        상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductException(
                                ProductErrorCode.UNKNOWN_PRODUCT,
                                "상품이 존재하지 않습니다. productId=" + productId
                        )
                );

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

        // 같은 상품 + 같은 shipment + READY 주문 있는지 확인
        Optional<Order> existingOrder =
                orderRepository.findByUserAndProductAndShipmentIdAndStatus(
                        user,
                        product,
                        shipmentId,
                        OrderStatus.READY
                );

        // 있으면 quantity 누적
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.addQuantity(quantity);
            return OrderResponse.from(order);
        }

//      없을 경우 주문 생성
        Order order = Order.builder()
                .shipmentId(shipmentId)
                .user(user)
                .product(product)
                .quantity(quantity)
                .totalPrice(product.getCost() * quantity)
                .status(OrderStatus.READY)
                .build();



        Order saveorder = orderRepository.save(order);
        return OrderResponse.from(saveorder);
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

        User user = order.getUser();

        orderRepository.delete(order);

        if (!orderRepository.existsByUser(user)) {
            userRepository.delete(user);
        }



    }
}
