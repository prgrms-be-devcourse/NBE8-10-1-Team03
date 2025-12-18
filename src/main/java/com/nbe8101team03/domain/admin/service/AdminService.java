package com.nbe8101team03.domain.admin.service;

import com.nbe8101team03.domain.admin.dto.AdminPasswordChangeRequest;
import com.nbe8101team03.domain.admin.dto.AdminRequest;
import com.nbe8101team03.domain.admin.dto.AdminResponse;
import com.nbe8101team03.domain.admin.dto.AdminUpdateRequest;
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
                    throw new AdminException(
                    AdminErrorCode.ADMIN_ALREADY_EXISTS,
                    "adminCreate error",
                    "admin already exist");
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
        List<Admin> admins = includeInactive
                ? adminRepository.findAll()
                : adminRepository.findAllByActiveTrue();

        return admins.stream()
                .map(a -> new AdminResponse(a.getId(), a.getUserId(), isActive(a)))
                .toList();
    }

    // 어드민 단건 조회
    public AdminResponse detail(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException(
                        AdminErrorCode.ADMIN_NOT_FOUND,
                        "adminDetail error",
                        "admin not found"
                ));

        return new AdminResponse(admin.getId(), admin.getUserId(), isActive(admin));
    }

    //    어드민 소프트 삭제
    @Transactional
    public void deactivate(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException(
                        AdminErrorCode.ADMIN_NOT_FOUND,
                        "adminDeactivate error",
                        "admin not found"
                ));

        if (!isActive(admin)) {
            throw new AdminException(
                    AdminErrorCode.ADMIN_ALREADY_DEACTIVATED,
                    "adminDeactivate error",
                    "admin already deactivated"
            );
        }
        admin.deactivate();
    }

    //    어드민 소프트 복구
    @Transactional
    public void activate(Long adminId) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException(
                        AdminErrorCode.ADMIN_NOT_FOUND,
                        "adminActivate error",
                        "admin not found"
                ));

        if (isActive(admin)) {
            throw new AdminException(
                    AdminErrorCode.ADMIN_ALREADY_ACTIVATED,
                    "adminActivate error",
                    "admin already activated"
            );
        }
        admin.activate();
    }

//    어드민 userId 수정
    @Transactional
    public AdminResponse update(Long adminId, AdminUpdateRequest request) {
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException(
                        AdminErrorCode.ADMIN_NOT_FOUND,
                        "adminUpdate error",
                        "admin not found"
                ));

        if (request.userId() != null && !request.userId().isBlank()
                && !request.userId().equals(admin.getUserId())) {

            if (adminRepository.existsByUserId(request.userId())) {
                throw new AdminException(
                        AdminErrorCode.ADMIN_ALREADY_EXISTS,
                        "adminUpdate error",
                        "admin already exist"
                );
            }

            admin.changeUserId(request.userId());
        }

        return new AdminResponse(admin.getId(), admin.getUserId(), admin.isActive());
    }

    @Transactional
    public void changePassword(Long adminId, AdminPasswordChangeRequest request){
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new AdminException(
                        AdminErrorCode.ADMIN_NOT_FOUND,
                        "adminChangePassword error",
                        "admin not found"
                ));

        // 현재 비밀번호 검증
        if(!PasswordEncoder.matches(request.currentPassword(), admin.getPasswordHash())){
            throw new AdminException(
                    AdminErrorCode.ADMIN_PASSWORD_MISMATCH,
                    "adminChangePassword error",
                    "current password mismatch"
            );
        }

        // 새 비밀번호와 기존 비밀번호가 동일한 것을 방지
        if(PasswordEncoder.matches(request.newPassword(), admin.getPasswordHash())){
            throw new AdminException(
                    AdminErrorCode.ADMIN_PASSWORD_SAME_AS_OLD,
                    "adminChangePassword error",
                    "new password same as old password"
            );
        }

        admin.changePasswordHash(PasswordEncoder.encode(request.newPassword()));
    }
    private boolean isActive(Admin admin) {
        return admin.isActive();
    }
}
