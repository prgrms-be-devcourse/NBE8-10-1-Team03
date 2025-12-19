package com.nbe8101team03.domain.admin.controller;

import com.nbe8101team03.domain.admin.dto.AdminLoginRequest;
import com.nbe8101team03.domain.admin.dto.AdminLoginResponse;
import com.nbe8101team03.domain.admin.dto.AdminRequest;
import com.nbe8101team03.domain.admin.dto.AdminResponse;
import com.nbe8101team03.domain.admin.service.AdminAuthService;
import com.nbe8101team03.domain.admin.service.AdminService;
import com.nbe8101team03.global.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminAuthController {
    private final AdminAuthService adminAuthService;
    private final AdminService adminService;


    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> login(@RequestBody @Valid AdminLoginRequest request){
        AdminLoginResponse res = adminAuthService.login(request);
        return ResponseEntity.ok(CommonResponse.success(res.accessToken(), "로그인 성공"));
    }

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<AdminResponse>> create(@RequestBody @Valid AdminRequest request) {
        AdminResponse res = adminService.create(request);
        return ResponseEntity.ok(CommonResponse.success(res, "어드민 생성 성공"));
    }
}
