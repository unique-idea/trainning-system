package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Location;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.dto.LocationDto;
import com.fptacademy.training.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class LocationMapper {

    private final ModelMapper modelMapper;

    public LocationDto toDto(Location location){
        if(location == null){
            return null;
        }

        LocationDto dto = modelMapper.map(location, LocationDto.class);
        return dto;
    }
}
