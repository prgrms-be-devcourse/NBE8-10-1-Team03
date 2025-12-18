package com.nbe8101team03.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminPasswordChangeRequest (
        @NotBlank
        @Size(min = 8, max = 32)
        String currentPassword,

        @NotBlank
        @Size(min = 8, max = 32)
        String newPassword
){

}
