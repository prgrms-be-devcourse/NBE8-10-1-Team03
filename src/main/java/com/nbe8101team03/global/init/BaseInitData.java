package com.nbe8101team03.global.init;

import com.nbe8101team03.domain.admin.entity.Admin;
import com.nbe8101team03.domain.admin.repository.AdminRepository;
import com.nbe8101team03.global.util.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BaseInitData implements ApplicationRunner {
    private final AdminRepository adminRepository;

    private String adminUserId = "root";
    private String adminPassword = "123123123";

    /**
     * 최초 관리자 생성
     * 간편한 디버깅을 위함
     */
    @Override
    @Transactional
    public void run(ApplicationArguments args){
        if(adminRepository.existsByUserId(adminUserId)) return;

        String encoded = PasswordEncoder.encode(adminPassword);

        Admin admin = Admin.builder()
                .userId(adminUserId)
                .passwordHash(encoded)
                .build();

        adminRepository.save(admin);
    }
}
