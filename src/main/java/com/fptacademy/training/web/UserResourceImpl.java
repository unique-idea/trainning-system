package com.fptacademy.training.web;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fptacademy.training.web.vm.NoNullRequiredUserVM;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.api.UserResource;
import com.fptacademy.training.web.vm.UserVM;

import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    private final ResourceLoader resourceLoader;

    @Override
    public ResponseEntity<UserDto> createUser(UserVM userVM) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userVM));
    }

    @Override
    public ResponseEntity<UserDto> deActiveUser() {
        return null;
    }

    @Override
    public ResponseEntity<UserDto> deleteUser(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deleteUser(id));
    }

    @Override
    public ResponseEntity<UserDto> deActive(Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.deActive(id));
    }

//    @Override
//    public ResponseEntity<List<UserDto>> getUsers(Integer pageNumber, Integer pageSize) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(userService.getUsers(pageNumber, pageSize));
//    }

    @Override
    public ResponseEntity<Optional<UserDto>> getUserByEmail(String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findUserByEmail(email));
    }

//    @Override
//    public ResponseEntity<List<UserDto>> getUsers(String email, String fullName, String code,
//            String levelName, String roleName, String birthdayFrom, String birthdayTo,
//            String status, String sort, Integer pageNumber, Integer pageSize) {
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(userService.getUsersByFilters(email, fullName, code, levelName,
//                        roleName, true, birthdayFrom, birthdayTo, status, sort, pageNumber, pageSize));
//    }

    public ResponseEntity<?> importUsersFromExcel(MultipartFile file) {
        this.userService.importUsersToDB(file);
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

    @Override
    public ResponseEntity<?> exportUsersToExcel(HttpServletResponse response) {
        response.setContentType("application/json");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=User_Export.xlsx";
        response.setHeader(headerKey, headerValue);
        userService.exportUsersToExcel(response);
        return ResponseEntity
                .ok(Map.of("Message", "Export users to excel successfully"));
    }

    @Override
    public ResponseEntity<Resource> downloadUserExcelTemplate(HttpServletResponse response) {
        Resource resource = resourceLoader.getResource("classpath:templates/User-Template.xlsx");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=User-Template.xlsx");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
