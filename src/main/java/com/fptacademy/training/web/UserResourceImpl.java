package com.fptacademy.training.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fptacademy.training.web.vm.NoNullRequiredUserVM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<UserDto> deleteUser(Long id) {
        UserDto deletedUser = userService.deleteUser(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deletedUser);
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
            String levelName, String roleName, Boolean activated, String birthday) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUsersByFilters(email, fullName, code, levelName, roleName, activated, birthday));
    }

    public ResponseEntity<?> importUsersFromExcel(MultipartFile file) {
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
    public ResponseEntity<?> changeRole(long id, String typeRole) {
        this.userService.changeRole(id, typeRole);
        return ResponseEntity
                .ok(Map.of("Message", "User's role change successfully"));
    }

    @Override
    public ResponseEntity<UserDto> getUserById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(NoNullRequiredUserVM noNullRequiredUserVM, Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserById(noNullRequiredUserVM, id));
    }

}
