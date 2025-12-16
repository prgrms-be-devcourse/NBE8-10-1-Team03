package com.nbe8101team03.domain.orders.repository;


import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    boolean existsByUser(User user);

    List<Order> findAllByUserOrderByOrderDateDesc(User user);
    List<Order> findAllByOrderDateBefore(LocalDateTime date);
}
