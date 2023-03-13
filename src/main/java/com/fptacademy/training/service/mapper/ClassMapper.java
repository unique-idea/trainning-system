package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.service.dto.ClassDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClassMapper {

    private final ModelMapper modelMapper;

    public ClassDto toDto(Class classes) {
        if (classes == null) {
            return null;
        }
        ClassDto dto = modelMapper.map(classes, ClassDto.class);

        dto.setCreated_by(classes.getCreatedBy() == null? null :
                new ClassDto.Creator(classes.getCreatedBy().getId(),
                        classes.getCreatedBy().getFullName()));

        if(classes.getClassDetail() != null){
            ClassDetail detail = classes.getClassDetail();
            dto.setAttendee(detail.getAttendee() == null? null:
                    new ClassDto.AttendeeSimplified(detail.getAttendee().getId(),
                            detail.getAttendee().getType()));
            dto.setLocation_id(detail.getLocation() == null? null:
                    new ClassDto.LocationSimplified(detail.getLocation().getId(),
                            detail.getLocation().getCity(),
                            detail.getLocation().getFsu()));
        }else{
            dto.setAttendee(null);
            dto.setLocation_id(null);
        }

        dto.setCreated_at(classes.getCreatedAt());
        return dto;
    }

}
