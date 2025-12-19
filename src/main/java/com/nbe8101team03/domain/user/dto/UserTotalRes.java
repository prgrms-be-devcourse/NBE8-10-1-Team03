package com.nbe8101team03.domain.user.dto;

import com.nbe8101team03.domain.user.entity.User;

public record UserTotalRes (
        Long userId,
        String email,
        String address,
        int zipcode,
        int totalSpent
) {
    public UserTotalRes(User user,int totalSpent) {
        this(
                user.getUserId(),
                user.getEmail(),
                user.getAddress(),
                user.getZipcode(),
                totalSpent
        );
    }
}