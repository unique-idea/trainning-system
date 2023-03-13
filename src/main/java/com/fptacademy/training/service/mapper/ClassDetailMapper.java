package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.ClassDetailDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ClassDetailMapper {

    private final ModelMapper modelMapper;


    public ClassDetailDto toDto(ClassDetail details) {
        if (details == null) {
            return null;
        }

        ClassDetailDto dto = modelMapper.map(details, ClassDetailDto.class);
        Class classes = details.getClassField();

        dto.setClass_id(new ClassDetailDto.ClassSimplified(classes.getId(),
                classes.getName(), classes.getCode(), classes.getDuration()));

        dto.setAttendee(details.getAttendee() == null? null:
                new ClassDetailDto.AttendeeSimplified(details.getAttendee().getId(),
                        details.getAttendee().getType()));

        dto.setLocation(details.getLocation() == null? null:
                new ClassDetailDto.LocationSimplified(details.getLocation().getId(),
                        details.getLocation().getCity(),
                        details.getLocation().getFsu()));

        if(details.getSchedules() != null){
            List<ClassSchedule> schedules = details.getSchedules();
            List<ClassDetailDto.Trainer> trainers = new ArrayList<>();
            for(ClassSchedule schedule: schedules){
                ClassDetailDto.Trainer trainer = new ClassDetailDto.Trainer(schedule.getTrainer().getId(),
                        schedule.getTrainer().getFullName());
                trainers.add(trainer);
            }
            dto.setTrainer(trainers);
        }else{
            dto.setTrainer(null);
        }

        return dto;
    }

}
