package com.nbe8101team03.domain.user.controller;

import com.nbe8101team03.domain.user.dto.UserInfoDto;
import com.nbe8101team03.domain.user.dto.UserInfoRes;
import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.service.UserService;
import com.nbe8101team03.global.response.CommonResponse;
import com.nbe8101team03.global.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PostMapping
    public ResponseEntity<Response<UserInfoRes>> create(@RequestBody UserInfoDto dto) {
        User user = userService.createUser(
                dto.email(),
                dto.address(),
                dto.zipcode()
        );
        return ResponseEntity.ok(CommonResponse.success(new UserInfoRes(user), "유저 생성을 성공하였습니다."));

    }

    @GetMapping
    public ResponseEntity<Response<List<UserInfoRes>>> getUsers(){
        List<User> userList = userService.findAll();

        List<UserInfoRes> userDtoList = userList.stream()
                                    .map(UserInfoRes::new)
                                    .toList();

        return ResponseEntity.ok(CommonResponse.success(userDtoList, "유저 조회 성공"));
    }

    // 아이디로 찾기
    @GetMapping("/{userid}")
    public ResponseEntity<Response<UserInfoRes>> getUserbyId(@PathVariable Long userid){
        User user = userService.findById(userid);

        return  ResponseEntity.ok(CommonResponse.success(new UserInfoRes(user), "유저 아이디 조회 성공"));
    }

    // 이메일로 찾기
    @GetMapping("/email/{email}")
    public ResponseEntity<Response<UserInfoRes>> getUserbyEmail(@PathVariable String email){
        User user = userService.findByEmail(email);

        return  ResponseEntity.ok(CommonResponse.success(new UserInfoRes(user), "유저 이메일 조회 성공"));
    }

    // 유저 수정
    @PutMapping("/{userId}")
    public ResponseEntity<Response<UserInfoRes>> modify( @PathVariable Long userId,
                                        @RequestBody UserInfoDto dto
    ) {
        User user = userService.findById(userId);
        userService.modify(user, dto.email(),dto.address(),dto.zipcode());

       return  ResponseEntity.ok(CommonResponse.success(new UserInfoRes(user), "유저 수정 성공"));
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Response<UserInfoRes>> delete(@PathVariable Long userId) {
        User user = userService.findById(userId);

        userService.delete(user);
        return ResponseEntity.ok(CommonResponse.success(null, "유저 삭제 성공"));
    }

}
