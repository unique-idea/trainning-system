package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.*;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.ClassScheduleService;
import com.fptacademy.training.service.dto.ReturnClassScheduleDto;
import com.fptacademy.training.service.dto.ReturnClassScheduleDtoOld;
import com.fptacademy.training.service.dto.ReturnUnitDto;
import com.fptacademy.training.service.dto.ReturnUserDto;
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

    private final UnitMapper unitMapper;

    public ReturnClassScheduleDtoOld toDTO(ClassSchedule classSchedule) {
        ReturnClassScheduleDtoOld result = new ReturnClassScheduleDtoOld();
        try {
            ClassDetail classDetail = classSchedule.getClassDetail();
            Class classField = classDetail.getClassField();
            Attendee attendee = classDetail.getAttendee();
            List<User> adminsUser = userRepository.findAdminsOfClass(classDetail.getId());
            List<ReturnUserDto> admins = new ArrayList<>();
            adminsUser.forEach(
                    a -> {
                        ReturnUserDto tmp = new ReturnUserDto(a.getFullName(), a.getId());
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
            ReturnUserDto trainer = toReturnUserDTO(classSchedule.getTrainer().getId());
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

    public List<ReturnClassScheduleDtoOld> toListDTO(List<ClassSchedule> classSchedules) {
        if (classSchedules == null) {
            return null;
        }
        List<ReturnClassScheduleDtoOld> result = new ArrayList<>();
        classSchedules.forEach(
                classScheduleTmp -> {
                    log.debug("Converting ClassSchedule to ClassScheduleDTO.......");
                    ReturnClassScheduleDtoOld tmp = toDTO(classScheduleTmp);
                    if (tmp != null) {
                        log.debug("Adding ClassScheduleDTO to result list.......");
                        result.add(tmp);
                    }
                }
        );
        return result;
    }

    //new version
    public ReturnClassScheduleDto toDTOReturnClassScheduleDto(ClassSchedule classSchedule) {
        ReturnClassScheduleDto result = new ReturnClassScheduleDto();
        try {
            ClassDetail classDetail = classSchedule.getClassDetail();
            Class classField = classDetail.getClassField();
            Attendee attendee = classDetail.getAttendee();
            List<ReturnUnitDto> units = new ArrayList<>();
            if (classSchedule.getSession() != null) {
                units = unitMapper.toListDto(classSchedule.getSession().getUnits());
            }

            List<User> adminsUser = userRepository.findAdminsOfClass(classDetail.getId());
            List<ReturnUserDto> admins = new ArrayList<>();
            adminsUser.forEach(
                    a -> {
                        ReturnUserDto tmp = new ReturnUserDto(a.getFullName(), a.getId());
                        admins.add(tmp);
                    }
            );

            List<User> trainersUser = userRepository.findTrainerOfClass(classDetail.getId());
            List<ReturnUserDto> trainers = new ArrayList<>();
            trainersUser.forEach(
                    t -> {
                        ReturnUserDto tmp = new ReturnUserDto(t.getFullName(), t.getId());
                        trainers.add(tmp);
                    }
            );

            int currentClassDay = classScheduleService.getCurrentClassDay(classDetail.getId(),
                    classSchedule.getId());

            result.setClassId(classField.getId());
            result.setClassCode(classField.getCode());
            result.setClassName(classField.getName());
            result.setDuration(classField.getDuration());
            result.setCurrentClassDay(currentClassDay);
            Location location = classDetail.getLocation();
            result.setCity(location.getCity());
            result.setFsu(location.getFsu());
            result.setType(attendee.getType());
            result.setTrainers(trainers);
            result.setAdmins(admins);
            result.setUnits(units);

            result.setDate(classSchedule.getStudyDate());
            result.setStartAt(classDetail.getStartAt());
            result.setFinishAt(classDetail.getFinishAt());

        } catch (Exception e) {
            log.debug(e.getMessage());
            return null;
        }
        return result;
    }

    public List<ReturnClassScheduleDto> toListReturnClassScheduleDto(List<ClassSchedule> classSchedules) {
        if (classSchedules == null) {
            return null;
        }
        List<ReturnClassScheduleDto> result = new ArrayList<>();
        classSchedules.forEach(
                classScheduleTmp -> {
                    log.debug("Converting ClassSchedule to ClassScheduleDTO.......");
                    ReturnClassScheduleDto tmp = toDTOReturnClassScheduleDto(classScheduleTmp);
                    if (tmp != null) {
                        log.debug("Adding ClassScheduleDTO to result list.......");
                        result.add(tmp);
                    }
                }
        );
        return result;
    }

    public ReturnUserDto toReturnUserDTO(Long id) {
        ReturnUserDto result = new ReturnUserDto();
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
