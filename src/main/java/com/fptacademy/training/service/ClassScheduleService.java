package com.fptacademy.training.service;

import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.domain.enumeration.ClassStatus;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.ClassScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClassScheduleService {
    private final ClassScheduleRepository classScheduleRepository;

    public List<ClassSchedule> getClassScheduleByDate(LocalDate date) {
        if (date == null) {
            throw new ResourceBadRequestException("date is null");
        }
        log.info("Getting all class by date from database.");
        List<ClassSchedule> result = classScheduleRepository.findActiveClassByStudyDate(date, ClassStatus.OPENNING.toString());
        if (result.isEmpty()) {
            log.error("There are no class at date {}", date);
            throw new ResourceNotFoundException("There are no class at date " + date);
        }
        return result;
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

    public List<ClassSchedule> getClassScheduleOfAUserByDate(LocalDate date, Long userId) {

        if (date == null || userId == null) {
            log.error("Bad request for date and userId value");
            throw new ResourceBadRequestException("Bad request for date and userId value");
        }
        log.debug("Getting class schedule of user from database.");
        List<ClassSchedule> result = classScheduleRepository.findActiveClassByUserIdAndStudyDate(userId, date, ClassStatus.OPENNING.toString());
        if (result.isEmpty()) {
            log.error("There are no class at date {} of user {}", date, userId);
            throw new ResourceNotFoundException("There are no class at date " + date + " of user " + userId);
        }
        return result;
    }

    public List<ClassSchedule> getClassScheduleOfAUserByDateBetween(Long userId,
                                                                    LocalDate startDate,
                                                                    LocalDate endDate) {

        if (userId == null || startDate == null || endDate == null) {
            throw new ResourceBadRequestException("Bad request");
        }
        log.debug("Getting class schedule of user from database.");
        List<ClassSchedule> result = classScheduleRepository
                .findActiveClassByUserIdAndStudyDateBetween(userId, startDate, endDate, ClassStatus.OPENNING.toString());

        if (result.isEmpty()) {
            log.error("There are no class between {} and {} of user {}", startDate, endDate, userId);
            throw new ResourceNotFoundException("There are no class between "
                    + startDate + " and " + endDate + " of user " + userId);
        }
        return result;
    }

    public List<ClassSchedule> getClassScheduleByDateBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new ResourceBadRequestException("Bad request");
        }
        log.debug("Getting all class schedule in a week.......");
        List<ClassSchedule> result = classScheduleRepository.findActiveClassByStudyDateBetween(startDate, endDate, ClassStatus.OPENNING.toString());
        if (result.isEmpty()) {
            log.error("There are no class between {} and {}", startDate, endDate);
            throw new ResourceNotFoundException("There are no class between "
                    + startDate + " and " + endDate);
        }
        return result;
    }

    public List<ClassSchedule> getClassScheduleOfAUserInAWeek(Long userId,
                                                              LocalDate date) {
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        LocalDate firstDayOfCurrentWeek = date
                .minusDays(date.getDayOfWeek()
                        .getValue() - firstDayOfWeek.getValue()
                );
        log.debug("First day of week is: {}", firstDayOfCurrentWeek);
        LocalDate endDayOfCurrentWeek = firstDayOfCurrentWeek.plusDays(6);
        log.debug("End day of week is: {}", endDayOfCurrentWeek);

        return getClassScheduleOfAUserByDateBetween(userId, firstDayOfCurrentWeek, endDayOfCurrentWeek);
    }

    public List<ClassSchedule> getClassScheduleInAWeek(LocalDate date) {

        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        LocalDate firstDayOfCurrentWeek = date
                .minusDays(date.getDayOfWeek()
                        .getValue() - firstDayOfWeek.getValue()
                );
        log.debug("First day of week is: {}", firstDayOfCurrentWeek);
        LocalDate endDayOfCurrentWeek = firstDayOfCurrentWeek.plusDays(6);
        log.debug("End day of week is: {}", endDayOfCurrentWeek);

        return getClassScheduleByDateBetween(firstDayOfCurrentWeek, endDayOfCurrentWeek);
    }

}
