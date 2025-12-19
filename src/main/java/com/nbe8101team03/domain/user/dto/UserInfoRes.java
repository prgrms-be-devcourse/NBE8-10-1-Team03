package com.nbe8101team03.domain.user.dto;

import com.nbe8101team03.domain.user.entity.User;

public record UserInfoRes (
        Long id,
        String email,
        String address,
        int zipcode
){
    public UserInfoRes(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getAddress(),
                user.getZipcode()
        );
    }
}
