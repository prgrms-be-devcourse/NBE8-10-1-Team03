package com.nbe8101team03.domain.admin.controller;

import com.nbe8101team03.domain.admin.dto.AdminRequest;
import com.nbe8101team03.domain.admin.dto.AdminResponse;
import com.nbe8101team03.domain.admin.service.AdminService;
import com.nbe8101team03.global.response.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //    어드민 생성
    @PostMapping
    public CommonResponse<AdminResponse> create(@RequestBody @Valid AdminRequest request) {
        AdminResponse res = adminService.create(request);
        return CommonResponse.success(res, "어드민 생성 성공");
    }

    //    어드민 list 조회
    @GetMapping
    public CommonResponse<List<AdminResponse>> list(
            @RequestParam(defaultValue = "false") boolean includeInactive
    ) {
        List<AdminResponse> res = adminService.list(includeInactive);
        return CommonResponse.success(res, "어드민 목록 조회 성공");
    }

    //    어드민 단건 조회
    @GetMapping("/{adminId}")
    public CommonResponse<AdminResponse> detail(@PathVariable Long adminId) {
        AdminResponse res = adminService.detail(adminId);
        return CommonResponse.success(res, "어드민 단건 조회 성공");
    }

    //    어드민 소프트 삭제
    @PatchMapping("/{adminId}/deactivate")
    public CommonResponse<Void> deactivate(@PathVariable Long adminId) {
        adminService.deactivate(adminId);
        return CommonResponse.success(null, "어드민 비활성화 성공");
    }

    //    주문 리스트
//    @GetMapping("/list")

    //    어드민 수정
//    @PutMapping("/{id}")

    //    userId기반 주문 리스트 확인
//    @GetMapping("/list/{userId}")

    //  주문 리스트에서 삭제
//    @DeleteMapping("/list/{id}")
}
