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
//        추후  User merge 되면 변경할예정
//        User newUser = User.builder()
//                .email(email)
//                .address(address)
//                .zipcode(zipcode)
//                .build();
        
//        입력값 검증
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

        if (!orderRepository.existsByUser(user)) {
            userRepository.delete(user);
        }



    }
}
