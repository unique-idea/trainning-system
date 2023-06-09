package com.fptacademy.training.web.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fptacademy.training.web.vm.AccountVM;
import com.fptacademy.training.web.vm.LoginVM;
import com.fptacademy.training.web.vm.TokenVM;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.validation.Valid;

@RequestMapping("/api/auth")
public interface AuthenticationResource {
    @Operation(summary = "Login entry",
            description = "Login entry",
            tags = "authentication"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Authenticated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body"),
            @ApiResponse(responseCode = "401", description = "Incorrect email or password", content = @Content)
    }
    )
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<AccountVM> login(@Valid @RequestBody LoginVM loginVM);

    @Operation(summary = "Get access token",
            description = "Get new access token from refresh token",
            tags = "authentication"
    )
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "401", description = "Missing or invalid refresh token", content = @Content)
    }
    )
    @GetMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TokenVM> getAccessTokenFromRefreshToken(@RequestHeader("Refresh-Token") String refreshToken);
}
