package com.fptacademy.training.web.api;

import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.web.vm.ProgramVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public interface ProgramResource {
    @Operation(
            summary = "Create a training program",
            description = "Create a training program",
            tags = "program",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created training program successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT"),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource"),
            @ApiResponse(responseCode = "409", description = "Conflict training program name")
    })
    @PostMapping(value = "/program", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramVM programVM);
}
