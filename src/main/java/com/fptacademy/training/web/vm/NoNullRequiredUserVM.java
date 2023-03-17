package com.fptacademy.training.web.vm;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

public record NoNullRequiredUserVM (

    @Pattern(regexp = "^[a-zA-Z ]{3,50}$", message = "Invalid name")
    @Schema(example = "Tran Huu Tri")
    String fullName,


    @Size(max = 50, message = "Invalid, Email too long")
    @Email(message = "Invalid email")
    String email,

    @Schema(example = "2001-04-13")
    String birthday,

    @Pattern(regexp = "^(male|female)$", message = "Gender must be 'male' or 'female'")
    @Schema(example = "male")
    String gender,

    @Pattern(regexp = "^(true|false)$", message = "Activated must be 'true' or 'false'")
    @Schema(example = "true")
    String activated,

    @Pattern(regexp = "^(basic|intermediate|advanced)$", message = "Level must one of 'basic', 'intermediate', 'advanced'")
    @Schema(example = "basic")
    String level,

    @Size(max = 50, message = "Invalid, password too long")
    String password,

    @Pattern(regexp = "^(on boarding|in class|off class|active|inactive)$", message = "Level must one of 'on boarding', 'in class', 'off class', 'active', 'inactive'")
    @Schema(example = "active")
    String status,

    String avatarUrl,

    String code

) {}
