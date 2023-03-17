package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.service.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleMapper {
    private final ModelMapper modelMapper;

    public RoleDto toDto(Role role){
        if(role==null){
            return null;
        }
        RoleDto dto=modelMapper.map(role,RoleDto.class);
        return dto;
    }
    public List<RoleDto> toDtos(List<Role>roles){
        return roles.stream().map(this::toDto).toList();
    }
}
