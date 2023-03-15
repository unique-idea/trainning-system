package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.*;
import com.fptacademy.training.domain.Class;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.ClassScheduleService;
import com.fptacademy.training.service.dto.ClassScheduleDTO;
import com.fptacademy.training.service.dto.ReturnUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleMapper {

    private final UserRepository userRepository; //replace with userService later

    private final ClassScheduleService classScheduleService;

    public ClassScheduleDTO toDTO(ClassSchedule classSchedule) {
        ClassScheduleDTO result = new ClassScheduleDTO();
        try {
            ClassDetail classDetail = classSchedule.getClassDetail();
            Class classField = classDetail.getClassField();
            Attendee attendee = classDetail.getAttendee();
            List<User> adminsUser = userRepository.findAdminsOfClass(classDetail.getId());
            List<ReturnUserDTO> admins = new ArrayList<>();
            adminsUser.forEach(
                    a -> {
                        ReturnUserDTO tmp = new ReturnUserDTO(a.getFullName(), a.getId());
                        admins.add(tmp);
                    }
            );

            int currentClassDay = classScheduleService.getCurrentClassDay(classDetail.getId(),
                    classSchedule.getId());

            result.setCode(classField.getCode());
            result.setName(classField.getName());
            result.setDuration(classField.getDuration());
            result.setCurrentClassDay(currentClassDay);
            Location location = classDetail.getLocation();
            result.setLocation(location.getCity() + "." + location.getFsu());
            ReturnUserDTO trainer = toReturnUserDTO(classSchedule.getTrainer().getId());
            result.setTrainer(trainer);
            result.setAdmins(admins);

            result.setType(attendee.getType());
            result.setDate(classSchedule.getStudyDate());
            result.setStartAt(classDetail.getStartAt());
            result.setFinishAt(classDetail.getFinishAt());
        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
        return result;
    }

    public List<ClassScheduleDTO> toListDTO(List<ClassSchedule> classSchedules) {
        if(classSchedules == null) {
            return null;
        }
        List<ClassScheduleDTO> result = new ArrayList<>();
        classSchedules.forEach(
                classScheduleTmp -> {
                    log.debug("Converting ClassSchedule to ClassScheduleDTO.......");
                    ClassScheduleDTO tmp = toDTO(classScheduleTmp);
                    if (tmp != null) {
                        log.debug("Adding ClassScheduleDTO to result list.......");
                        result.add(tmp);
                    }
                }
        );
        return result;
    }

    public ReturnUserDTO toReturnUserDTO(Long id) {
        ReturnUserDTO result = new ReturnUserDTO();
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        result.setName(user.getFullName());
        result.setId(user.getId());
        return result;
    }

//    public ClassScheduleDTO toDTO(Class classEntity,
//                                  ClassDetail classDetail,
//                                  ClassSchedule classSchedule,
//                                  Attendee attendee,
//                                  List<Long> adminIds,
//                                  int currentClassDay) {
//        ClassScheduleDTO result = new ClassScheduleDTO();
//
//        result.setCode(classEntity.getCode());
//        result.setName(classEntity.getName());
//        result.setDuration(classEntity.getDuration());
//        result.setCurrentClassDay(currentClassDay);
//        Location location = classDetail.getLocation();
//        result.setLocation(location.getCity() + "." + location.getFsu());
//
//        ReturnUserDTO trainer = toReturnUserDTO(classSchedule.getTrainer().getId());
//        result.setTrainer(trainer);
//        List<ReturnUserDTO> admins = new ArrayList<>();
//        adminIds.forEach(
//                id -> {
//                    ReturnUserDTO tmp = toReturnUserDTO(id);
//                    admins.add(tmp);
//                }
//        );
//
//        result.setType(attendee.getType());
//        result.setDate(classSchedule.getStudyDate());
//        result.setStartAt(classDetail.getStartAt());
//        result.setFinishAt(classDetail.getFinishAt());
//
//        return result;
//    }

}
