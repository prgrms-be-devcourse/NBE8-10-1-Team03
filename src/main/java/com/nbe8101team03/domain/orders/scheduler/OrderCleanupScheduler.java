package com.nbe8101team03.domain.orders.scheduler;

import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderCleanupScheduler {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

//    매일 새벽 3시에 실행

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteOldOrders() {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);

        List<Order> oldOrders = orderRepository.findAllByOrderDateBefore(twoDaysAgo);

        for(Order order : oldOrders) {
            User user = order.getUser();

            orderRepository.delete(order);

            if (orderRepository.existsByUser(user)) {
                orderRepository.deleteAll(oldOrders);
                userRepository.delete(user);
            }
        }
    }
}
