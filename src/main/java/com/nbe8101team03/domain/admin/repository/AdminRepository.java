package com.nbe8101team03.domain.admin.repository;

import com.nbe8101team03.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    boolean existsByUserId(String userId);
}
