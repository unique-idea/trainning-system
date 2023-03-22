package com.fptacademy.training.service.mapper;

import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.*;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.ClassScheduleService;
import com.fptacademy.training.service.dto.ReturnClassScheduleDto;
import com.fptacademy.training.service.dto.ReturnUnitDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleMapper {

    private final UserRepository userRepository;

    private final ClassScheduleService classScheduleService;

    private final UnitMapper unitMapper;

    //new version
    public ReturnClassScheduleDto toReturnClassScheduleDto(ClassSchedule classSchedule) {
        ReturnClassScheduleDto result = new ReturnClassScheduleDto();
        try {
            //if classDetail or classField or attendee is null then return null throw try catch
            ClassDetail classDetail = classSchedule.getClassDetail();
            Class classField = classDetail.getClassField();
            Attendee attendee = classDetail.getAttendee();
            List<ReturnUnitDto> units = new ArrayList<>();
            if (classSchedule.getSession() != null) {
                units = unitMapper.toListDto(classSchedule.getSession().getUnits());
            }

            int currentClassDay = classScheduleService.getCurrentClassDay(classDetail.getId(),
                    classSchedule.getId());
            Location location = classDetail.getLocation();

            result.setClassId(classField.getId());
            result.setClassCode(classField.getCode());
            result.setClassName(classField.getName());
            result.setDuration(classField.getDuration());
            result.setCurrentClassDay(currentClassDay);

            if (location != null) {
                String city = location.getCity();
                String fsu = location.getFsu();
                if (city != null) {
                    result.setCity(city);
                }
                if (fsu != null) {
                    result.setFsu(fsu);
                }
            }

            result.setType(attendee.getType());
            result.setUnits(units);

            result.setDate(classSchedule.getStudyDate());
            result.setStartAt(classDetail.getStartAt());
            result.setFinishAt(classDetail.getFinishAt());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return result;
    }

    public List<ReturnClassScheduleDto> toListReturnClassScheduleDto(List<ClassSchedule> classSchedules) {
        if (classSchedules == null || classSchedules.isEmpty()) {
            return null;
        }
        List<ReturnClassScheduleDto> result = new ArrayList<>();
        classSchedules.forEach(
                classScheduleTmp -> {
                    log.debug("Converting ClassSchedule to ClassScheduleDTO.......");
                    ReturnClassScheduleDto tmp = toReturnClassScheduleDto(classScheduleTmp);
                    if (tmp != null) {
                        log.debug("Adding ClassScheduleDTO to result list.......");
                        result.add(tmp);
                    }
                }
        );
        return result;
    }

}
