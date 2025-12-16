package com.nbe8101team03.domain.admin.controller;

import com.nbe8101team03.domain.admin.dto.AdminRequest;
import com.nbe8101team03.domain.admin.service.AdminService;
import com.nbe8101team03.global.response.CommonResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admins")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //    어드민 생성
    @PostMapping
    public CommonResponse<Long> create(@RequestBody @Valid AdminRequest request) {
        Long id = adminService.adminCreate(request).id();
        return CommonResponse.success(id, "어드민 생성 성공");
    }

    //    어드민 조회 단건? 다건?
//    @GetMapping


    //    주문 리스트
//    @GetMapping("/list")

    //    어드민 수정
//    @PutMapping("/{id}")

    //    userId기반 주문 리스트 확인
//    @GetMapping("/list/{userId}")

    //  주문 리스트에서 삭제
//    @DeleteMapping("/list/{id}")
}
