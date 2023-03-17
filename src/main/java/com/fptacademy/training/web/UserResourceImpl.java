package com.fptacademy.training.web;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.api.UserResource;
import com.fptacademy.training.web.vm.UserVM;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> createUser(UserVM userVM) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userVM));
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsers(Integer pageNumber, Integer pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers(pageNumber, pageSize));
    }

    @Override
    public ResponseEntity<Optional<UserDto>> getUserByEmail(String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findUserByEmail(email));
    }

    @Override
    public ResponseEntity<List<UserDto>> getUsersByFilters(String email, String fullName, String code,
            String levelName, String roleName, Boolean activated, String birthdayFrom, String birthdayTo, 
            String status, String sort, Integer pageNumber, Integer pageSize) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsersByFilters(email, fullName, code, levelName,
                        roleName, false, birthdayFrom, birthdayTo, status, sort, pageNumber, pageSize));
    }
}
