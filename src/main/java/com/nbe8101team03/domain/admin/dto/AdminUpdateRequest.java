package com.nbe8101team03.domain.admin.dto;

import jakarta.validation.constraints.Size;

public record AdminUpdateRequest (
        @Size(max = 10)
        String userId,
        @Size(min = 4, max = 255)
        String password
){
}
