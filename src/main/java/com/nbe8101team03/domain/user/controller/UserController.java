package com.nbe8101team03.domain.user.controller;

import com.nbe8101team03.domain.user.dto.UserDto;
import com.nbe8101team03.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//    POST/users : 유저 생성
//    GET/users : 유저 전체 조회
//    GET/users /{userId} : 유저 단건 조회
//    PUT/users /{userId} : 유저 수정
//    DELETE/users/{userId} : 유저 삭제


}
