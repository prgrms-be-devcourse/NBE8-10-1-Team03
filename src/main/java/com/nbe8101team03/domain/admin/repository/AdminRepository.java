package com.nbe8101team03.domain.admin.repository;

import com.nbe8101team03.domain.admin.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByUserId(String userId);
    List<Admin> findAllByActiveTrue();
    Optional<Admin> findByUserId(String userId);
}
