package com.fptacademy.training.web;

import com.fptacademy.training.service.UserService;
import com.fptacademy.training.web.api.UserResource;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserResourceImpl implements UserResource {
    private final UserService userService;



}
