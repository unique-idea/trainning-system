package com.fptacademy.training.service;

import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.ClassScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClassScheduleServiceTest {

    @Mock
    private ClassScheduleRepository classScheduleRepository;

    @InjectMocks
    private ClassScheduleService classScheduleService;

    private ClassSchedule classSchedule;
    private User trainer;

    @BeforeEach
    void setUp() {
        trainer = new User();
        trainer.setFullName("Dao Minh Tri");
        trainer.setId(1L);

        classSchedule = new ClassSchedule();
        classSchedule.setId(1L);
        classSchedule.setStudyDate(LocalDate.of(2023, 3, 17));
        classSchedule.setTrainer(trainer);
    }

    @Test
    @DisplayName("Test getClassScheduleByDate case 1")
    void getClassScheduleByDateShouldReturnAList() {
        //given
        List<ClassSchedule> classScheduleList = new ArrayList<>();
        classScheduleList.add(classSchedule);
        given(classScheduleRepository.findActiveClassByStudyDate(any(LocalDate.class)))
                .willReturn(classScheduleList);
        //when
        List<ClassSchedule> result = classScheduleService
                .getClassScheduleByDate(LocalDate.of(2013, 3, 1));

        //then
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(classScheduleRepository).findActiveClassByStudyDate(any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getClassScheduleByDate case 2")
    void getClassScheduleByDateShouldThrowException1() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleByDate(null);
        })
                .hasMessage("date is null")
                .isInstanceOf(ResourceBadRequestException.class)
        ;

        verify(classScheduleRepository, never()).findActiveClassByStudyDate(any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getClassScheduleByDate case 3")
    void getClassScheduleByDateShouldThrowException2() {
        //given
        List<ClassSchedule> classScheduleList = new ArrayList<>();
        given(classScheduleRepository.findActiveClassByStudyDate(LocalDate.of(2023, 3, 10)))
                .willReturn(classScheduleList);
        //when
        //then
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleByDate(LocalDate.of(2023, 3, 10));
        })
                .hasMessage("There are no class at date " + LocalDate.of(2023, 3, 10))
                .isInstanceOf(ResourceNotFoundException.class)
        ;
        verify(classScheduleRepository).findActiveClassByStudyDate(any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getCurrentClassDay case 1")
    void getCurrentClassDayShouldReturnAnInteger() {
        //given
        given(classScheduleRepository.getCurrentClassDayOfClassSchedule(anyLong(), anyLong()))
                .willReturn(3);
        //when
        Integer result = classScheduleService.getCurrentClassDay(anyLong(), anyLong());
        //then
        assertEquals(3, result);
        verify(classScheduleRepository).getCurrentClassDayOfClassSchedule(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Test getCurrentClassDay case 2")
    void getCurrentClassDayShouldThrowException() {
        //given
        //when
        //then
        assertThatThrownBy(
                () -> classScheduleService.getCurrentClassDay(null, 1L)
        )
                .hasMessage("Bad request for classId and classScheduleId")
                .isInstanceOf(ResourceBadRequestException.class);

        assertThatThrownBy(
                () -> classScheduleService.getCurrentClassDay(1L, null)
        )
                .hasMessage("Bad request for classId and classScheduleId")
                .isInstanceOf(ResourceBadRequestException.class);

        verify(classScheduleRepository, never()).getCurrentClassDayOfClassSchedule(null, 1L);
    }

    @Test
    @DisplayName("Test getCurrentClassDay case 3")
    void getCurrentClassDayShouldReturnDefaultValue() {
        //given
        given(classScheduleRepository.getCurrentClassDayOfClassSchedule(anyLong(), anyLong()))
                .willReturn(null);
        //when
        Integer result = classScheduleService.getCurrentClassDay(anyLong(), anyLong());
        //then
        assertEquals(-1, result);

        verify(classScheduleRepository).getCurrentClassDayOfClassSchedule(anyLong(), anyLong());
    }

    @Test
    @DisplayName("Test getClassScheduleOfAUserByDate case 1")
    void getClassScheduleOfAUserByDateShouldReturnAList() {
        //given
        List<ClassSchedule> classSchedules = new ArrayList<>();
        classSchedules.add(classSchedule);
        given(classScheduleRepository.findActiveClassByUserIdAndStudyDate(any(Long.class), any(LocalDate.class)))
                .willReturn(classSchedules);
        //when
        List<ClassSchedule> result = classScheduleService.getClassScheduleOfAUserByDate(LocalDate.now(), 1L);
        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(classScheduleRepository).findActiveClassByUserIdAndStudyDate(any(Long.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getClassScheduleOfAUserByDate case 2")
    void getClassScheduleOfAUserByDateShouldThrowException1() {
        //given
        List<ClassSchedule> classSchedules = new ArrayList<>();
        given(classScheduleRepository.findActiveClassByUserIdAndStudyDate(any(Long.class), any(LocalDate.class)))
                .willReturn(classSchedules);
        //when
        //then
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleOfAUserByDate(LocalDate.of(2022, 3, 3), 1L);
        })
                .hasMessage("There are no class at date " + LocalDate.of(2022, 3, 3)
                        + " of user 1")
                .isInstanceOf(ResourceNotFoundException.class);
        verify(classScheduleRepository).findActiveClassByUserIdAndStudyDate(any(Long.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getClassScheduleOfAUserByDate case 3")
    void getClassScheduleOfAUserByDateShouldThrowException2() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleOfAUserByDate(null, 1L);
        })
                .hasMessage("Bad request for date and userId value")
                .isInstanceOf(ResourceBadRequestException.class);
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleOfAUserByDate(LocalDate.now(), null);
        })
                .hasMessage("Bad request for date and userId value")
                .isInstanceOf(ResourceBadRequestException.class);

        verify(classScheduleRepository, never()).findActiveClassByUserIdAndStudyDate(any(Long.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getClassScheduleOfAUserByDateBetween case 1")
    void getClassScheduleOfAUserByDateBetweenShouldReturnAList() {
        //given
        List<ClassSchedule> classSchedules = new ArrayList<>();
        classSchedules.add(classSchedule);
        given(classScheduleRepository
                .findActiveClassByUserIdAndStudyDateBetween(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .willReturn(classSchedules);
        //when
        List<ClassSchedule> result = classScheduleService.getClassScheduleOfAUserByDateBetween(
                1L,
                LocalDate.of(2023, 3, 10),
                LocalDate.of(2023, 3, 15));
        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(classScheduleRepository).findActiveClassByUserIdAndStudyDateBetween(
                anyLong(),
                any(LocalDate.class),
                any(LocalDate.class)
        );
    }

    @Test
    @DisplayName("Test getClassScheduleOfAUserByDateBetween case 2")
    void getClassScheduleOfAUserByDateBetweenShouldThrowException1() {
        //given
        List<ClassSchedule> classSchedules = new ArrayList<>();
        given(classScheduleRepository
                .findActiveClassByUserIdAndStudyDateBetween(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .willReturn(classSchedules);
        //when
        //then
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleOfAUserByDateBetween(
                    1L,
                    LocalDate.of(2023, 3, 10),
                    LocalDate.of(2023, 3, 15));
        })
                .hasMessage("There are no class between "
                        + LocalDate.of(2023, 3, 10) + " and "
                        + LocalDate.of(2023, 3, 15)
                        + " of user 1")
                .isInstanceOf(ResourceNotFoundException.class);

        verify(classScheduleRepository).findActiveClassByUserIdAndStudyDateBetween(
                anyLong(),
                any(LocalDate.class),
                any(LocalDate.class)
        );
    }

    @Test
    @DisplayName("Test getClassScheduleOfAUserByDateBetween case 3")
    void getClassScheduleOfAUserByDateBetweenShouldThrowException2() {
        //given
        //when
        //then
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleOfAUserByDateBetween(
                    null,
                    LocalDate.of(2023, 3, 10),
                    LocalDate.of(2023, 3, 15));
        })
                .hasMessage("Bad request")
                .isInstanceOf(ResourceBadRequestException.class);

        verify(classScheduleRepository, never()).findActiveClassByUserIdAndStudyDateBetween(
                anyLong(),
                any(LocalDate.class),
                any(LocalDate.class)
        );
    }

    @Test
    @DisplayName("Test getClassScheduleByDateBetween case 1")
    void getClassScheduleByDateBetweenShouldReturnAList() {
        //given
        List<ClassSchedule> classSchedules = new ArrayList<>();
        classSchedules.add(classSchedule);
        given(classScheduleRepository.findActiveClassByStudyDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .willReturn(classSchedules);
        //when
        List<ClassSchedule> result = classScheduleService.getClassScheduleByDateBetween(
                LocalDate.of(2023, 3, 10),
                LocalDate.of(2023, 3, 15)
        );
        //then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(classScheduleRepository).findActiveClassByStudyDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getClassScheduleByDateBetween case 2")
    void getClassScheduleByDateBetweenShouldThrowException1() {
        //given
        List<ClassSchedule> classSchedules = new ArrayList<>();
        given(classScheduleRepository.findActiveClassByStudyDateBetween(any(LocalDate.class), any(LocalDate.class)))
                .willReturn(classSchedules);
        //when
        //then
        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleByDateBetween(
                    LocalDate.of(2023, 3, 10),
                    LocalDate.of(2023, 3, 15)
            );
        })
                .hasMessage("There are no class between "
                        + LocalDate.of(2023, 3, 10) + " and "
                        + LocalDate.of(2023, 3, 15))
                .isInstanceOf(ResourceNotFoundException.class);
        verify(classScheduleRepository).findActiveClassByStudyDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    @DisplayName("Test getClassScheduleByDateBetween case 3")
    void getClassScheduleByDateBetweenShouldThrowException2() {
        //given
        //when
        //then

        assertThatThrownBy(() -> {
            classScheduleService.getClassScheduleByDateBetween(
                    null, null
            );
        })
                .hasMessage("Bad request")
                .isInstanceOf(ResourceBadRequestException.class);
        verify(classScheduleRepository, never()).findActiveClassByStudyDateBetween(any(LocalDate.class), any(LocalDate.class));
    }

    @Test
    @Disabled
    void getClassScheduleOfAUserInAWeek() {
    }

    @Test
    @Disabled
    void getClassScheduleInAWeek() {
    }
}