package com.nbe8101team03.domain.orders.service;

import com.nbe8101team03.domain.orders.dto.OrderResponse;
import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.entity.OrderStatus;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
import com.nbe8101team03.domain.product.entity.Product;
import com.nbe8101team03.domain.product.repository.ProductRepository;
import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


//    주문하기
    public OrderResponse createOrder(String email, String address, int zipcode, Long productId , int quantity) {
//       유저생성
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setAddress(address);
                    newUser.setZipcode(zipcode);
                    return userRepository.save(newUser);
                });


//        상품 조회
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다. productId=" + productId));

//        주문가격 계산
        int totalPrice = product.getCost() * quantity;


//        주문 생성
        Order order = Order.builder()
                .user(user)
                .product(product)
                .quantity(quantity)
                .status(OrderStatus.READY)
                .totalPrice(totalPrice)
                .build();


        Order saveorder = orderRepository.save(order);
        return OrderResponse.from(saveorder);
    }


    //    주문 조회
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow();

        return OrderResponse.from(order);
    }

//    유저별 주문 조회
    @Transactional(readOnly = true)
    public List<OrderResponse> getOrdersByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        List<Order> orders = orderRepository.findAllByUserOrderByOrderDateDesc(user);

        return orders.stream()
                .map(OrderResponse::from)
                .toList();

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
        Order order = orderRepository.findById(orderId).orElseThrow();
        User user = order.getUser();

        orderRepository.delete(order);

        if (!orderRepository.existsByUser(user)) {
            userRepository.delete(user);
        }

    }
}
