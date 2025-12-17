package com.nbe8101team03.domain.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdminPasswordChangeRequest (
        @NotBlank
        String currentPassword,

        @NotBlank
        @Size(min = 4, max = 255)
        String newPassword
){

}
