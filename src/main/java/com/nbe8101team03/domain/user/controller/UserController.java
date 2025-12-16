package com.nbe8101team03.domain.user.controller;

import com.nbe8101team03.domain.user.dto.UserInfoDto;
import com.nbe8101team03.domain.user.dto.UserInfoRes;
import com.nbe8101team03.domain.user.entity.User;
import com.nbe8101team03.domain.user.service.UserService;
import com.nbe8101team03.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @PostMapping
    public CommonResponse<UserInfoRes> create(@RequestBody UserInfoDto dto) {
        User user = userService.createUser(
                dto.email(),
                dto.address(),
                dto.zipcode()
        );
        return CommonResponse.success(new UserInfoRes(user), "유저 생성 성공");
    }

    @GetMapping
    public CommonResponse<List<UserInfoRes>> getUsers(){
        List<User> userList = userService.findAll();

        List<UserInfoRes> userDtoList = userList.stream()
                                    .map(UserInfoRes::new)
                                    .toList();

        return CommonResponse.success(userDtoList, "유저 조회 성공");
    }

    // 아이디로 찾기
    @GetMapping("/{userid}")
    public CommonResponse<UserInfoRes> getUserbyId(@PathVariable int userid){
        User user = userService.findById(userid).get();

        return  CommonResponse.success(new UserInfoRes(user), "유저 아이디 조회 성공");
    }

    // 이메일로 찾기
    @GetMapping("/{email}")
    public CommonResponse<UserInfoRes> getUserbyEmail(@PathVariable String email){
        User user = userService.findByEmail(email).get();

        return  CommonResponse.success(new UserInfoRes(user), "유저 이메일 조회 성공");
    }

    // 유저 수정
    @PutMapping("/{userId}")
    public CommonResponse<UserInfoRes> modify( @PathVariable int id,
                                        @RequestBody UserInfoDto dto
    ) {
        User user = userService.findById(id).get();
        userService.modify(user, dto.email(),dto.address(),dto.zipcode());

       return  CommonResponse.success(new UserInfoRes(user), "유저 수정 성공");
    }

    // 유저 삭제
    @DeleteMapping("/{userId}")
    public CommonResponse<UserInfoRes> delete(@PathVariable int id) {
        User user = userService.findById(id).get();

        userService.delete(user);
        return CommonResponse.success(null, "유저 삭제 성공");
    }

}
