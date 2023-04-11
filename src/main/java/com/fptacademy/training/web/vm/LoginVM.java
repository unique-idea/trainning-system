package com.fptacademy.training.web.vm;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public record LoginVM(
        @Schema(example = "admin@gmail.com") @NotBlank(message = "Email is required")
        String email,
        @Schema(example = "12345") @NotBlank(message = "Password is required")
        String password) {
}
