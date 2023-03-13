package com.fptacademy.training.service.mapper;


import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.LevelService;
import com.fptacademy.training.service.RoleService;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.vm.UserVM;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public UserDto toDto(User user){
        if(user == null){
            return null;
        }
        UserDto dto = modelMapper.map(user, UserDto.class);
        return dto;
    }

    public User toEntity(UserVM userVM, LevelService levelService, RoleService roleService){
        if(userVM == null && levelService == null && roleService == null){
            return null;
        }
        User user = new User();
        if(userVM != null){
            user.setFullName(userVM.fullName());
            user.setEmail(userVM.email());
            user.setBirthday(LocalDate.parse(userVM.birthday()));
            user.setGender(Boolean.valueOf(userVM.gender()));
            user.setActivated(Boolean.valueOf(userVM.activated()));
            user.setAvatarUrl(userVM.avatarUrl());
            user.setPassword(userVM.password());
            user.setCode(userVM.code());
        }
        user.setLevel(levelService.getLevelByName(userVM.level()));
        user.setRole(roleService.getRoleByName(userVM.role()));
        return user;
    }

    public List<UserDto> toDtos(List<User> users) {
        return users.stream().map(this::toDto).toList();
    }

}
