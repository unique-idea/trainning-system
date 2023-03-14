package com.fptacademy.training.web.api;

import com.fptacademy.training.web.vm.LoginVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RequestMapping("/api/auth")
public interface AuthenticationResource {
    @Operation(summary = "Login entry",
            description = "Login entry",
            tags = "authentication"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(responseCode = "401", description = "Incorrect email or password")
    }
    )
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    void login(@RequestBody LoginVM loginVM, HttpServletResponse response);

    @Operation(summary = "Get access token",
            description = "Get new access token from refresh token",
            tags = "authentication"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid refresh token")
    }
    )
    @GetMapping(value = "/refresh")
    void getAccessTokenFromRefreshToken(@RequestHeader("refresh_token") String refreshToken, HttpServletResponse response);
}
