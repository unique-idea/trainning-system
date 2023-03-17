package com.fptacademy.training.web.api;

import com.fptacademy.training.service.dto.ClassScheduleDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/api")
@EnableMethodSecurity
public interface ClassScheduleResource {

    @Operation(
            summary = "Get class schedule by date",
            description = "Get class schedule by date",
            tags = "class schedule",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get class schedule successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Class schedule not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('Class_FullAccess')")
    @GetMapping(value = "/admin/calendar/{day}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ClassScheduleDTO>> getAllClassSchedule(@PathVariable("day") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);


    @Operation(
            summary = "Get class schedule of user by date",
            description = "Get class schedule of user by date",
            tags = "class schedule",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get class schedule successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Class schedule not found", content = @Content)
    })
    @PreAuthorize("hasAuthority('Class_FullAccess')")
    @GetMapping(value = "/admin/week/calendar/{day}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ClassScheduleDTO>> getAllClassScheduleByWeek(@PathVariable("day") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);


    @Operation(
            summary = "Get class schedule of user by date",
            description = "Get class schedule of user by date",
            tags = "class schedule",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get class schedule successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Class schedule not found", content = @Content)
    })
//    @PreAuthorize("hasAuthority('Class_FullAccess')")
    @GetMapping(value = "/calendar/{day}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ClassScheduleDTO>> getAllClassScheduleOfCurrentUser(@PathVariable("day") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);

    @Operation(
            summary = "Get class schedule of user by date",
            description = "Get class schedule of user by date",
            tags = "class schedule",
            security = @SecurityRequirement(name = "token_auth")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get class schedule successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "404", description = "Class schedule not found", content = @Content)
    })
//    @PreAuthorize("hasAuthority('Class_FullAccess')")
    @GetMapping(value = "/week/calendar/{day}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ClassScheduleDTO>> getAllClassScheduleOfCurrentUserByWeek(@PathVariable("day") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date);
}
