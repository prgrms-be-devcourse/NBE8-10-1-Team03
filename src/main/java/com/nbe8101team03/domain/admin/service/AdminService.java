package com.nbe8101team03.domain.admin.service;

import com.nbe8101team03.domain.admin.dto.AdminRequest;
import com.nbe8101team03.domain.admin.dto.AdminResponse;
import com.nbe8101team03.domain.admin.entity.Admin;
import com.nbe8101team03.domain.admin.repository.AdminRepository;
import com.nbe8101team03.global.exception.errorCode.AdminErrorCode;
import com.nbe8101team03.global.exception.exception.AdminException;
import com.nbe8101team03.global.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    // 어드민 생성
    @Transactional
    public AdminResponse create(AdminRequest adminRequest) {
        if (adminRepository.existsByUserId(adminRequest.userId())) {
            throw new AdminException(AdminErrorCode.ADMIN_UNDEFINED_ERROR,
                    "adminCreate Error", "undefined error");
        }

        Admin admin = Admin.builder()
                .userId(adminRequest.userId())
                .passwordHash(PasswordEncoder.encode(adminRequest.password()))
                .build();

        Admin saved = adminRepository.save(admin);

        return new AdminResponse(saved.getId(), saved.getUserId(), saved.isActive());
    }

    /**
     * 어드민 목록 조회
     * includeInactive=true : 전체 admin 반환
     * includeInactive=false : active admin 반환
     */
    public List<AdminResponse> list(boolean includeInactive) {
        return adminRepository.findAll().stream()
                .filter(a -> includeInactive || isActive(a))
                .map(a -> new AdminResponse(a.getId(), a.getUserId(), isActive(a)))
                .toList();
    }

    // 어드민 단건 조회
    public AdminResponse detail(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException(
                        AdminErrorCode.ADMIN_UNDEFINED_ERROR,
                        "adminDetail Error",
                        "undefined error"
                ));

        return new AdminResponse(admin.getId(), admin.getUserId(), isActive(admin));
    }

    private boolean isActive(Admin admin) {
        return admin.isActive();
    }
}
