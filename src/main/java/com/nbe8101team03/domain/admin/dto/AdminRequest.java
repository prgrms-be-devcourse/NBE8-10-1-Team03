package com.nbe8101team03.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminRequest(
        @NotBlank
        @Size(max = 10)
        String userId,

        @NotBlank
        @Size(min = 8, max = 32)
        String password
) {
}
