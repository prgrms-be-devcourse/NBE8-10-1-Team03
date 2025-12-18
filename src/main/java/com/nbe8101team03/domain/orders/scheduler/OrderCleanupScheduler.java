package com.nbe8101team03.domain.orders.scheduler;

import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.entity.OrderStatus;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
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

//    매일 새벽 3시에 실행

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteOldOrders() {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);

        List<Order> oldOrders =
                orderRepository.findAllByStatusAndOrderDateBefore(
                        OrderStatus.COMPLETED,
                        twoDaysAgo
                );

        for (Order order : oldOrders) {
            orderRepository.delete(order);
        }
    }
}
