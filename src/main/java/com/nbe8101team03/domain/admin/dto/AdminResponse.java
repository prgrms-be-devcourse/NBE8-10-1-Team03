package com.nbe8101team03.domain.admin.dto;

public record AdminResponse (
        Long id,
        String userId,
        boolean active
) {}
