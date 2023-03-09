package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Program;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.ProgramDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProgramMapper {
    private final ModelMapper modelMapper;
    private final SyllabusRepository syllabusRepository;

    public ProgramDto toDto(Program program) {
        if (program == null) {
            return null;
        }
        ProgramDto dto = modelMapper.map(program, ProgramDto.class);
        int duration = (int)program.getSyllabuses()
                .stream()
                .map(Syllabus::getSessions)
                .count();
        dto.setCreatedBy(new ProgramDto.Creator(program.getCreatedBy().getId(), program.getCreatedBy().getFullName()));
        dto.setDuration(duration);
        return dto;
    }
}
