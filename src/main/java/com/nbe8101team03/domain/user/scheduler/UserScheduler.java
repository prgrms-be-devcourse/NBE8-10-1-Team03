package com.nbe8101team03.domain.user.scheduler;


import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.orders.entity.OrderStatus;
import com.nbe8101team03.domain.orders.repository.OrderRepository;
import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.repository.UserRepository;
import com.nbe8101team03.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserScheduler {
    private final UserService userService;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Transactional
    @Scheduled(fixedRate = 3600000) // 1시간마다 실행되도록 설정
    public void UserDeativeOrders() {
        List<Order> orderList = orderRepository.findAll();

        // 구매가 종료되면 유저를 비활성화 시키기 위해..
        // 1시간마다 모든 오더 조회 => 오더 상태가 COMPLETED인 것만 골라서
        // 해당 오더에 연결된 유저를 비활성화
        for (Order order : orderList) {
            if (order.getStatus() == OrderStatus.COMPLETED) {
                User targetUser = order.getUser();
                userService.deactivateUser(targetUser);
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시에 실행
    public void UserDeleteOrders() {
        // 매일 새벽 3시에 실행,
        // 비활성화된 유저만 골라서 하드 딜리트

        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            if (!user.isActive()) {
                userService.hardDelete(user);
            }
        }
    }
}
