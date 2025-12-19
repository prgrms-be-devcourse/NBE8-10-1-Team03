package com.nbe8101team03.domain.admin.controller;

import com.nbe8101team03.domain.admin.dto.AdminLoginRequest;
import com.nbe8101team03.domain.admin.dto.AdminLoginResponse;
import com.nbe8101team03.domain.admin.dto.AdminRequest;
import com.nbe8101team03.domain.admin.dto.AdminResponse;
import com.nbe8101team03.domain.admin.service.AdminAuthService;
import com.nbe8101team03.global.response.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins/auth")
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    public AdminAuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

//    not used
//    @PostMapping("/login")
//    public ResponseEntity<CommonResponse<AdminLoginRequest>> login(@RequestBody @Valid AdminLoginRequest request){
//        AdminLoginResponse res = adminAuthService.login(request);
//        return ResponseEntity.ok(CommonResponse.success(res, "로그인 성공"));
//    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<String>> login(@RequestBody @Valid AdminLoginRequest request){
        AdminLoginResponse res = adminAuthService.login(request);
        return ResponseEntity.ok(CommonResponse.success(res.accessToken(), "로그인 성공"));
    }
}
