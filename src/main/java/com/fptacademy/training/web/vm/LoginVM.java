package com.fptacademy.training.web.vm;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginVM(
        @Schema(example = "tien@gmail.com")
        String email,
        @Schema(example = "12345")
        String password) {
}
