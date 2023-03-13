package com.fptacademy.training.web;

import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.api.UserResource;
import com.fptacademy.training.web.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<UserDto>> getUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsers());
    }

    @Override
    public ResponseEntity<Optional<UserDto>> getUserByEmail(String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findUserByEmail(email));
    }
}
