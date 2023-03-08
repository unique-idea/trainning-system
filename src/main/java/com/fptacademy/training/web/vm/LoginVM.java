package com.fptacademy.training.web.vm;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginVM(
        @Schema(example = "hiepnguyen@gmail.com")
        String email,
        @Schema(example = "mysecuredpassword")
        String password) {
}
