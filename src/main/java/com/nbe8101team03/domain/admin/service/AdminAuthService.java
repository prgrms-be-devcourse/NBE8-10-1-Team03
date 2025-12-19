package com.nbe8101team03.domain.admin.service;

import com.nbe8101team03.domain.admin.dto.AdminLoginRequest;
import com.nbe8101team03.domain.admin.dto.AdminLoginResponse;
import com.nbe8101team03.domain.admin.entity.Admin;
import com.nbe8101team03.domain.admin.repository.AdminRepository;
import com.nbe8101team03.global.exception.errorCode.AdminAuthErrorCode;
import com.nbe8101team03.global.exception.exception.AdminAuthException;
import com.nbe8101team03.global.util.JwtUtil;
import com.nbe8101team03.global.util.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminAuthService {
    private final AdminRepository adminRepository;
    private final JwtUtil jwtUtil;

    private static final long EXPIRES_IN_SECONDS = 60 * 60 * 10; // 10시간

    public AdminAuthService(AdminRepository adminRepository, JwtUtil jwtUtil) {
        this.adminRepository = adminRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public AdminLoginResponse login(AdminLoginRequest request) {
        Admin admin = adminRepository.findByUserId(request.userId())
                .orElseThrow(() -> new AdminAuthException(
                        AdminAuthErrorCode.ADMIN_LOGIN_FAILED,
                        "ADMIN_LOGIN_FAILED",
                        "id is incorrect"
                ));

        if (!admin.isActive()) {
            throw new AdminAuthException(
                    AdminAuthErrorCode.ADMIN_INACTIVE,
                    "ADMIN_INACTIVE",
                    "admin is already inactive"
            );
        }

//        비밀번호 검증
        if (!PasswordEncoder.matches(request.password(), admin.getPasswordHash())) {
            throw new AdminAuthException(
                    AdminAuthErrorCode.ADMIN_LOGIN_FAILED,
                    "ADMIN_LOGIN_FAILED",
                    "password is incorrect"
            );
        }

        String token = jwtUtil.generateToken(admin.getUserId(), "ADMIN");
        if(token == null){
            throw new AdminAuthException(
                    AdminAuthErrorCode.ADMIN_TOKEN_GENERATION_FAILED,
                    "ADMIN_TOKEN_GENERATION_FAILED",
                    "token generation failed"
            );
        }
        return new AdminLoginResponse(token, "Bearer", EXPIRES_IN_SECONDS);
    }
}
