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
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserScheduler {
    private final UserService userService;
    private final UserRepository userRepository;


    @Transactional
    @Scheduled(cron = "0 0 3 * * *") // 매일 새벽 3시에 실행
    public void UserDeleteOrders() {
        // 매일 새벽 3시에 실행, 오더가 없는 고아 유저만 골라서 하드 딜리트

        List<User> userList = userRepository.findAll(); // 모든 유저 리스트로 가져옴
        Set<Long> orderedUserSet = userRepository.findAll_ordered_userid();
        // 오더에 속한 모든 유저 아이디를 set으로 가져옴


        for (User user : userList) {
            // 모든 유저 순회 :
            // 해당 유저가 orderedUserSet 에 속해있다? -> 통과
            // 해당 유저가 orderedUserSet 에 없다? -> 하드 딜리트
            if (!orderedUserSet.contains(user.getUser_id())) {
                userService.delete(user);
            }
        }
    }
}
