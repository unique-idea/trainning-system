package com.fptacademy.training.web.vm;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record UserVM(

        @NotBlank(message = "Full name must not be empty")
        @Pattern(regexp = "^[a-zA-Z ]{3,50}$", message = "Invalid name")
        @Schema(example = "Tran Huu Tri")
        String fullName,

        @NotBlank(message = "Email must be not empty")
        String email,

        @NotBlank
        @Schema(example = "2001-04-13")
        String birthday,

        @NotNull
        @Pattern(regexp = "^(male|female)$", message = "Gender must be 'male' or 'female'")
        @Schema(example = "male")
        String gender,

        @NotBlank(message = "Status must be not empty")
        @Schema(example = "true")
        String activated,

        @NotBlank(message = "Level must be not empty")
        String level,

        @NotBlank(message = "Role must be not empty")
        String role,

        @NotBlank(message = "AvatarUrl must be not empty")
        String avatarUrl,

        @NotBlank(message = "Password must be not empty")
        String password,

        @NotBlank(message = "Code must be not empty")
        String code
) {}
