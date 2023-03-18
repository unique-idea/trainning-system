package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.domain.User;
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
        dto.setStart_at(details.getStartAt());
        dto.setFinish_at(details.getFinishAt());

        Class classes = details.getClassField();
        User createdBy = classes.getCreatedBy();

        dto.setClass_id(new ClassDetailDto.ClassSimplified(
                classes.getId(),
                classes.getName(),
                classes.getCode(),
                classes.getDuration(),
                new ClassDetailDto.UserSimplified(
                        createdBy.getId(),
                        createdBy.getFullName(),
                        createdBy.getEmail(),
                        createdBy.getCode()),
                classes.getCreatedAt()
                ));

        dto.setAttendee(details.getAttendee() == null? null:
                new ClassDetailDto.AttendeeSimplified(
                        details.getAttendee().getId(),
                        details.getAttendee().getType()));

        dto.setLocation(details.getLocation() == null? null:
                new ClassDetailDto.LocationSimplified(
                        details.getLocation().getId(),
                        details.getLocation().getCity(),
                        details.getLocation().getFsu()));

        if (details.getSchedules() != null) {

            List<ClassSchedule> schedules = details.getSchedules();
            List<ClassDetailDto.ScheduleSimplified> simpleSchedules = new ArrayList<>();

            for(ClassSchedule schedule : schedules) {
                ClassDetailDto.ScheduleSimplified simpleSchedule = new ClassDetailDto.ScheduleSimplified (
                        schedule.getStudyDate(),
                        schedule.getSession().getUnits().stream().map(u -> new ClassDetailDto.UnitSimplified(
                                u.getId(),
                                u.getIndex(),
                                u.getName()
                        )).toList()
                );
                simpleSchedules.add(simpleSchedule);
            }
            dto.setSchedules(simpleSchedules);
        }
        else {
            dto.setSchedules(null);
        }

        if (details.getUsers() != null) {

            List<ClassDetailDto.UserSimplified> Trainers = new ArrayList<>();
            List<ClassDetailDto.UserSimplified> Admins = new ArrayList<>();

            for(User user: details.getUsers()){

                if(user.getRole().getName().equals("Super Admin") || user.getRole().getName().equals("Class Admin")){
                    Admins.add(new ClassDetailDto.UserSimplified(user.getId(), user.getFullName(), user.getEmail(),
                            user.getCode()));
                }

                else if (user.getRole().getName().equals("Trainer")) {
                    Trainers.add(new ClassDetailDto.UserSimplified(user.getId(), user.getFullName(), user.getEmail(),
                            user.getCode()));
                }
            }

            dto.setTrainer(Trainers);
            dto.setAdmin(Admins);

        }
        else {
            dto.setTrainer(null);
            dto.setAdmin(null);
        }

        return dto;
    }

}