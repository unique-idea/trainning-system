package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserDto toDto(User user){
        if(user == null){
            return null;
        }

        UserDto dto = modelMapper.map(user, UserDto.class);
        dto.setName(user.getFullName());
        return dto;
    }

}
