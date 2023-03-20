package com.fptacademy.training.web;

import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.ClassScheduleService;
import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.ReturnClassScheduleDto;
import com.fptacademy.training.service.mapper.ClassScheduleMapper;
import com.fptacademy.training.web.api.ClassScheduleResource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleResourceImpl implements ClassScheduleResource {

    private final ClassScheduleService classScheduleService;
    private final ClassScheduleMapper classScheduleMapper;
    private final UserService userService;

    @Override
    public ResponseEntity<List<ReturnClassScheduleDto>> getAllClassSchedule(LocalDate date) {

        List<ClassSchedule> classSchedules = classScheduleService.getClassScheduleByDate(date);
        List<ReturnClassScheduleDto> result = classScheduleMapper.toListReturnClassScheduleDto(classSchedules);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ReturnClassScheduleDto>> getAllClassScheduleByWeek(LocalDate date) {

        List<ClassSchedule> classSchedules = classScheduleService.getClassScheduleInAWeek(date);
        List<ReturnClassScheduleDto> result = classScheduleMapper.toListReturnClassScheduleDto(classSchedules);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ReturnClassScheduleDto>> getAllClassScheduleOfCurrentUser(LocalDate date) {

        User currentUser = userService.getCurrentUserLogin();
        List<ClassSchedule> classSchedules = classScheduleService.getClassScheduleOfAUserByDate(date, currentUser.getId());
        List<ReturnClassScheduleDto> result = classScheduleMapper.toListReturnClassScheduleDto(classSchedules);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ReturnClassScheduleDto>> getAllClassScheduleOfCurrentUserByWeek(LocalDate date) {

        User currentUser = userService.getCurrentUserLogin();
        List<ClassSchedule> classSchedules = classScheduleService.getClassScheduleOfAUserInAWeek(currentUser.getId(), date);
        List<ReturnClassScheduleDto> result = classScheduleMapper.toListReturnClassScheduleDto(classSchedules);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
