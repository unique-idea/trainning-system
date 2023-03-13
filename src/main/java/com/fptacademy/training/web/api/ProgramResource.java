package com.fptacademy.training.web.api;

import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.service.dto.SyllabusDto;
import com.fptacademy.training.web.vm.ProgramVM;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict training program name", content = @Content)
    })
    @PostMapping(value = "/programs", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramVM programVM);


    @Operation(
            summary = "Get list of training programs",
            description = "Get list of training programs with sort and pagination",
            tags = "program",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found training programs"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/programs", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProgramDto>> getPrograms(
            @RequestParam(value = "q", required = false) List<String> keywords,
            @RequestParam(value = "sort", required = false, defaultValue = "id,asc") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size);
    @Operation(
            summary = "Get list of syllabuses by name",
            description = "Get list of syllabuses by name",
            tags = "syllabuses",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found syllabuses"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Program id not found", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/syllabuses", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<SyllabusDto.SyllabusListDto>>getSyllabusesByName(@RequestParam String name);
}

