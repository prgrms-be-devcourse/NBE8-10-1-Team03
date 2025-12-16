package com.nbe8101team03.domain.user.dto;


public record UserInfoDto(
        String email,
        String address,
        int zipcode
) {

}