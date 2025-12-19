package com.nbe8101team03.domain.orders.scheduler;

import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.entity.OrderStatus;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {
    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 14 * * *")
    @Transactional
    public void startDelivery() {
        List<Order> readyOrders =
                orderRepository.findAllByStatus(OrderStatus.READY);

        readyOrders.forEach(order ->
                order.changeStatus(OrderStatus.DELIVERING)
        );
    }

    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void completeDelivery() {
        List<Order> deliveringOrders =
                orderRepository.findAllByStatus(OrderStatus.DELIVERING);

        deliveringOrders.forEach(order ->
                order.changeStatus(OrderStatus.COMPLETED)
        );
    }

}
