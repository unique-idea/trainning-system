package com.fptacademy.training.web;

import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.api.UserResource;
import com.fptacademy.training.web.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> uploadUserData(MultipartFile file) {
        this.userService.saveUsersToDB(file);
        return ResponseEntity
                .ok(Map.of("Message", "Users data uploaded and saved database successfully"));
    }

    @Override
    public ResponseEntity<List<UserDto>> getUserByName(String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findUserByName(name));
    }

    @Override
    public ResponseEntity<UserDto> changeRole(long id, long typeRole) {
        UserDto userDto = userService.changeRole(id, typeRole);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userDto);
    }
}
