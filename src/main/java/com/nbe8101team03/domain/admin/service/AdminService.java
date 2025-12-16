package com.nbe8101team03.domain.admin.service;

import com.nbe8101team03.domain.admin.dto.AdminCreateRequest;
import com.nbe8101team03.domain.admin.dto.AdminResponse;
import com.nbe8101team03.domain.admin.entity.Admin;
import com.nbe8101team03.domain.admin.repository.AdminRepository;
import com.nbe8101team03.global.exception.errorCode.AdminErrorCode;
import com.nbe8101team03.global.exception.exception.AdminException;
import com.nbe8101team03.global.util.SimplePasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {
    private AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    @Transactional
    public AdminResponse adminCreate(AdminCreateRequest adminCreateRequest){
        if(adminRepository.existsByUserId(adminCreateRequest.userId())){
            throw new AdminException(AdminErrorCode.ADMIN_UNDEFINED_ERROR,
                    "adminCreate Error", "server error");
        }

        Admin admin = Admin.builder()
                .userId(adminCreateRequest.userId())
                .password(SimplePasswordEncoder.encode(adminCreateRequest.password()))
                .build();

        Admin saved = adminRepository.save(admin);

        return new AdminResponse(saved.getId(), saved.getUserId());
    }
}
