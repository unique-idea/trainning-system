package com.fptacademy.training.repository;

import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.domain.enumeration.ClassStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ClassScheduleRepositoryTest {

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    @Test
    void findFilterActiveClassByStudyDateShouldWork() {
        LocalDate date = LocalDate.now();

        List<ClassSchedule> classSchedules = classScheduleRepository.findFilterActiveClassByStudyDate(
                date,
                ClassStatus.OPENNING.toString(),
                null,
                null,
                "abc",
                "Ho Chi Minh"
        );

        assertNotNull(classSchedules);
    }

    @Test
    void findFilterActiveClassByStudyDateBetweenShouldWork() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();

        List<ClassSchedule> classSchedules = classScheduleRepository.findFilterActiveClassByStudyDateBetween(
                startDate,
                endDate,
                ClassStatus.OPENNING.toString(),
                null,
                null,
                "abc",
                "Ho Chi Minh"
        );

        assertNotNull(classSchedules);
    }

    @Test
    void getCurrentClassDayOfClassSchedule() {
        Integer result = classScheduleRepository.getCurrentClassDayOfClassSchedule(1L, 1L);
        assertNotNull(result);
    }
}
