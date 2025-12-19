package com.nbe8101team03.domain.admin.dto;

public record AdminLoginResponse (
        String accessToken,
        String tokenType,
        long expiresInSeconds
) {}
