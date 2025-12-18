package com.nbe8101team03.domain.user.controller;

import com.nbe8101team03.domain.user.service.UserService;
import com.nbe8101team03.global.response.CommonResponse;
import com.nbe8101team03.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/check")
    public ResponseEntity<Response<Boolean>> isExistEmail(@RequestParam String email) {
        Boolean res = userService.isExistUser(email);
        return ResponseEntity.ok(CommonResponse.success(res));
    }

//    POST/users : 유저 생성
//    GET/users : 유저 전체 조회
//    GET/users /{userId} : 유저 단건 조회
//    PUT/users /{userId} : 유저 수정
//    DELETE/users/{userId} : 유저 삭제


}
