package com.nbe8101team03.domain.user.repository;

import com.nbe8101team03.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
