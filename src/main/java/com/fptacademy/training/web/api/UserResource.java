package com.fptacademy.training.web.api;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.vm.UserVM;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RequestMapping("/api")
public interface UserResource {
    @Operation(summary = "Create a user", description = "Create a user", tags = "user", security = @SecurityRequirement(name = "token_auth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created user successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
            @ApiResponse(responseCode = "409", description = "Conflict user name", content = @Content)
    })
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> createUser(@RequestBody @Valid UserVM userVM);

    @Operation(summary = "Get list of users", description = "Get list of users with sort and pagination", tags = "user", security = @SecurityRequirement(name = "token_auth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserDto>> getUsers(@RequestParam Integer pageNumber, @RequestParam Integer pageSize);

    @Operation(summary = "Get user by email", description = "Get user by email", tags = "user", security = @SecurityRequirement(name = "token_auth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Optional<UserDto>> getUserByEmail(@PathVariable String email);

    @PostMapping("/users/import")
    ResponseEntity<?> uploadUserData(@RequestParam("file") MultipartFile file);

    @Operation(summary = "Get user by name", description = "Get user by name", tags = "user", security = @SecurityRequirement(name = "token_auth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserDto>> getUserByName(@PathVariable String name);

    @Operation(summary = "Change role", description = "Change role", tags = "user", security = @SecurityRequirement(name = "token_auth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Change successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> changeRole(@PathVariable long id, String typeRole);

    @Operation(summary = "Delete user", description = "Delete user by id", tags = "user", security = @SecurityRequirement(name = "token_auth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Delete successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @DeleteMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> deleteUser(@PathVariable("id") Long id);

    @Operation(summary = "Get users by filters", description = "Get users by filters", tags = "user", security = @SecurityRequirement(name = "token_auth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found users"),
            @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized, missing or invalid JWT", content = @Content),
            @ApiResponse(responseCode = "403", description = "Access denied, do not have permission to access this resource", content = @Content),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/users/filters", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserDto>> getUsersByFilters(
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "name", required = false) String fullName,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "level", required = false) String levelName,
            @RequestParam(name = "role", required = false) String roleName,
            @RequestParam(name = "activated", required = false) Boolean activated,
            @RequestParam(name = "birthdayFrom", required = false) String birthdayFrom,
            @RequestParam(name = "birthdayTo", required = false) String birthdayTo,
            @RequestParam(name = "status", required = false) String status,
            @RequestParam(name = "sort", required = false, defaultValue = "id,asc") String sort,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer pageSize);
}
