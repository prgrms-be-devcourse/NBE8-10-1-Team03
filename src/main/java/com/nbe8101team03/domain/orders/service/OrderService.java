package com.nbe8101team03.domain.orders.service;

import com.nbe8101team03.domain.orders.dto.CreateOrderRequest;
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
import com.nbe8101team03.global.exception.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


//    주문하기
    public void createOrder(CreateOrderRequest dto) {
//       유저생성
        User user = userRepository.findByEmail(dto.email())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(dto.email())
                            .address(dto.address())
                            .zipcode(dto.zipcode())
                            .build();
                    return userRepository.save(newUser);
                });

        Map<Long, Integer> quantityMap = dto.details().stream()
                                                 .collect(Collectors.toMap(
                                                         CreateOrderRequest.OrderDetail::productId,
                                                         CreateOrderRequest.OrderDetail::quantity,
                                                         Integer::sum
                                                 ));

        Set<Long> productIds = quantityMap.keySet();
        List<Product> products = productRepository.findAllByIdIn(productIds);

        if(productIds.size() != products.size()) {
            throw new OrderException(OrderErrorCode.INVALID_QUANTITY);
        }

        List<Order> orders = new ArrayList<>();
        for(Product product : products) {
            orders.add(Order.builder()
                            .user(user)
                            .product(product)
                            .quantity(quantityMap.get(product.getId()))
                            .status(OrderStatus.READY)
                            .totalPrice(product.getCost() * quantityMap.get(product.getId()))
                            .build());
        }

        orderRepository.saveAll(orders);
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
            orderRepository.findAllByUserOrderByOrderDateDesc(user)
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

        if (orderRepository.existsByUser(user)) {
            userRepository.delete(user);
        }



    }
}
