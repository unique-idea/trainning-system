package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Lesson;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.domain.*;
import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.service.dto.SyllabusDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SyllabusMapper {
    private final ModelMapper modelMapper;

    public SyllabusDto.SyllabusListDto toDto(Syllabus syllabus){
        if(syllabus == null){
            return null;
        }
        SyllabusDto.SyllabusListDto dto =modelMapper.map(syllabus, SyllabusDto.SyllabusListDto.class);
        dto.setCreatedBy(syllabus.getCreatedBy().getCode());
        int durationInDays = syllabus.getSessions().size();
        int durationInMinutes = (int)syllabus.getSessions().stream()
                .flatMap(s -> s.getUnits().stream())
                .flatMap(u -> u.getLessons().stream())
                .mapToLong(Lesson::getDuration)
                .sum();
        dto.setDuration(durationInDays);
        dto.setDurationInHours(durationInMinutes / 60.f);
        return dto;
    }

    public List<SyllabusDto.SyllabusListDto> toDtos(List<Syllabus> syllabusList) {
        return syllabusList.stream().map(this::toDto).toList();
    }
}
