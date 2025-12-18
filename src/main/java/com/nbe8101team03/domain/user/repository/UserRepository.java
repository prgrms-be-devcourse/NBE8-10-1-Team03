package com.nbe8101team03.domain.user.repository;

import com.nbe8101team03.domain.orders.entity.Order;
import com.nbe8101team03.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT o.user.user_id FROM Order o")
    Set<Long> findAll_ordered_userid();
    // 오더에 속한 유저 아이디를 set으로 전부 가져옴

}
