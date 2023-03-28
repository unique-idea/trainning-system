package com.fptacademy.training.service;

import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.domain.enumeration.ClassStatus;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.repository.ClassScheduleRepository;
import com.fptacademy.training.service.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleService {
    private final ClassScheduleRepository classScheduleRepository;
    private final UserService userService;

    public List<ClassSchedule> getClassScheduleByDate(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (className == null && classCode == null && city == null)
            return getClassScheduleByDate(date);
        return getFilterClassScheduleByDate(date, className, classCode, city);
    }

    public List<ClassSchedule> getClassScheduleByDate(LocalDate date) {
        if (date == null) {
            throw new ResourceBadRequestException("Bad request: Date is null");
        }
        return classScheduleRepository.findActiveClassByStudyDate(date, ClassStatus.OPENNING.toString());
    }

    public List<ClassSchedule> getFilterClassScheduleByDate(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (date == null) {
            throw new ResourceBadRequestException("Bad request: date is null");
        }
        return classScheduleRepository.findFilterActiveClassByStudyDate(
                date,
                ClassStatus.OPENNING.toString(),
                null,
                className,
                classCode,
                city
        );
    }

    public List<ClassSchedule> getClassScheduleOfCurrentUserByDate(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (className == null && classCode == null && city == null)
            return getClassScheduleOfCurrentUserByDate(date);
        return getFilterClassScheduleOfCurrentUserByDate(date, className, classCode, city);
    }

    public List<ClassSchedule> getClassScheduleOfCurrentUserByDate(LocalDate date) {

        if (date == null) {
            throw new ResourceBadRequestException("Bad request for date and userId value");
        }
        User user = userService.getCurrentUserLogin();
        return classScheduleRepository.findActiveClassByUserIdAndStudyDate(user.getId(), date, ClassStatus.OPENNING.toString());
    }

    public List<ClassSchedule> getFilterClassScheduleOfCurrentUserByDate(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (date == null) {
            throw new ResourceBadRequestException("Bad request for date and userId value");
        }
        User user = userService.getCurrentUserLogin();
        return classScheduleRepository.findFilterActiveClassByStudyDate(
                date,
                ClassStatus.OPENNING.toString(),
                user.getId(),
                className,
                classCode,
                city
        );
    }

    public List<ClassSchedule> getClassScheduleOfCurrentUserInAWeek(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (className == null && classCode == null && city == null)
            return getClassScheduleOfCurrentUserInAWeek(date);
        return getFilterClassScheduleOfCurrentUserInAWeek(date, className, classCode, city);
    }

    public List<ClassSchedule> getClassScheduleOfCurrentUserInAWeek(LocalDate date) {
        if (date == null) {
            throw new ResourceBadRequestException("Bad request: date is null.");
        }
        LocalDate firstDate = DateTimeUtil.getFirstDateOfCurrentWeek(date);
        LocalDate lastDate = DateTimeUtil.getLastDateOfCurrentWeek(date);
        User user = userService.getCurrentUserLogin();
        return getClassScheduleOfAUserByDateBetween(user.getId(), firstDate, lastDate);
    }

    public List<ClassSchedule> getFilterClassScheduleOfCurrentUserInAWeek(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (date == null) {
            throw new ResourceBadRequestException("Bad request");
        }
        LocalDate firstDate = DateTimeUtil.getFirstDateOfCurrentWeek(date);
        LocalDate lastDate = DateTimeUtil.getLastDateOfCurrentWeek(date);
        User user = userService.getCurrentUserLogin();
        return classScheduleRepository.findFilterActiveClassByStudyDateBetween(
                firstDate,
                lastDate,
                ClassStatus.OPENNING.toString(),
                user.getId(),
                className,
                classCode,
                city
        );
    }

    public List<ClassSchedule> getClassScheduleInAWeek(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (className == null && classCode == null && city == null)
            return getClassScheduleInAWeek(date);
        return getFilterClassScheduleInAWeek(date, className, classCode, city);
    }

    public List<ClassSchedule> getClassScheduleInAWeek(LocalDate date) {

        if (date == null) {
            throw new ResourceBadRequestException("Bad request: date is null.");
        }
        LocalDate firstDate = DateTimeUtil.getFirstDateOfCurrentWeek(date);
        LocalDate lastDate = DateTimeUtil.getLastDateOfCurrentWeek(date);

        return getClassScheduleByDateBetween(firstDate, lastDate);
    }

    public List<ClassSchedule> getFilterClassScheduleInAWeek(
            LocalDate date,
            String className,
            String classCode,
            String city
    ) {
        if (date == null) {
            throw new ResourceBadRequestException("Bad request: date is null.");
        }
        LocalDate firstDate = DateTimeUtil.getFirstDateOfCurrentWeek(date);
        LocalDate lastDate = DateTimeUtil.getLastDateOfCurrentWeek(date);

        return classScheduleRepository.findFilterActiveClassByStudyDateBetween(
                firstDate,
                lastDate,
                ClassStatus.OPENNING.toString(),
                null,
                className,
                classCode,
                city
        );
    }

    public List<ClassSchedule> getClassScheduleOfAUserByDateBetween(
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    ) {

        if (userId == null || startDate == null || endDate == null) {
            throw new ResourceBadRequestException("Bad request");
        }
        return classScheduleRepository
                .findActiveClassByUserIdAndStudyDateBetween(userId, startDate, endDate, ClassStatus.OPENNING.toString());
    }

    public List<ClassSchedule> getClassScheduleByDateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new ResourceBadRequestException("Bad request");
        }
        return classScheduleRepository.findActiveClassByStudyDateBetween(startDate, endDate, ClassStatus.OPENNING.toString());
    }

    public int getCurrentClassDay(Long classId, Long classScheduleId) {
        if (classId == null || classScheduleId == null) {
            throw new ResourceBadRequestException("Bad request for classId and classScheduleId");
        }
        Integer day = classScheduleRepository.getCurrentClassDayOfClassSchedule(classId, classScheduleId);
        if (day == null) {
            return -1;
        }
        return day;
    }

}
