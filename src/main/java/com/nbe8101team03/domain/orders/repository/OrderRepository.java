package com.nbe8101team03.domain.orders.repository;


import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.entity.OrderStatus;
import com.nbe8101team03.domain.product.entity.Product;
import com.nbe8101team03.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByUser(User user);

    List<Order> findAllByUserOrderByOrderDateDesc(User user);

    @Query("SELECT o.user.userId FROM Order o")
    Set<Long> findAllOrderedUserId();
    // 오더에 속한 유저 아이디를 set으로 전부 가져옴
    List<Order> findAllByUserOrderByOrderDateAsc(User user);


    Optional<Order> findFirstByUserAndStatusAndOrderDateBetween(
            User user,
            OrderStatus status,
            LocalDateTime start,
            LocalDateTime end
    );


    List<Order> findAllByStatus(OrderStatus status);

    //    기존주문에 수량 추가하기
    Optional<Order> findByUserAndProductAndShipmentIdAndStatus(
        User user,
        Product product,
        Long shipmentId,
        OrderStatus status
    );

// 삭제 스케쥴러
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
        DELETE FROM Order o
        WHERE o.status = :status
          AND o.orderDate < :date
    """)
    int deleteOldOrders(@Param("status") OrderStatus status,
                        @Param("date") LocalDateTime date);


}
