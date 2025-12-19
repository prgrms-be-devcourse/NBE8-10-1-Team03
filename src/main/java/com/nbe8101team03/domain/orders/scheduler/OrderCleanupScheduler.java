package com.nbe8101team03.domain.orders.scheduler;

import com.nbe8101team03.domain.orders.entity.OrderStatus;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderCleanupScheduler {

    private final OrderRepository orderRepository;

//    매일 새벽 3시에 실행

    @Transactional
    @Scheduled(cron = "0 0 3 * * *")
    public void deleteOldOrders() {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);
        orderRepository.deleteOldOrders(OrderStatus.COMPLETED, twoDaysAgo);
    }
}
