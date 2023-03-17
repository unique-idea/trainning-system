package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Attendee;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.dto.AttendeeDto;
import com.fptacademy.training.service.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AttendeeMapper {

    private final ModelMapper modelMapper;

    public AttendeeDto toDto(Attendee attendee){
        if(attendee == null){
            return null;
        }

        AttendeeDto dto = modelMapper.map(attendee, AttendeeDto.class);
        return dto;
    }
}
