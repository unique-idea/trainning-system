package com.fptacademy.training.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fptacademy.training.domain.Attendee;
import com.fptacademy.training.domain.Class;
import com.fptacademy.training.domain.ClassDetail;
import com.fptacademy.training.domain.ClassSchedule;
import com.fptacademy.training.domain.Location;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.ClassScheduleService;
import com.fptacademy.training.service.dto.ClassScheduleDTO;
import com.fptacademy.training.service.dto.ReturnUserDTO;

@ExtendWith(MockitoExtension.class)
class ClassScheduleMapperTest {
    @Mock
    private UserRepository userRepository; //replace with userService later

    @Mock
    private ClassScheduleService classScheduleService;

    @InjectMocks
    private ClassScheduleMapper classScheduleMapper;

    private User trainer, admin1, admin2;
    private Class classField;
    private Location location;
    private Attendee attendee;
    private List<User> admin;
    private ClassSchedule classScheduleMock = mock(ClassSchedule.class);
    private ClassDetail classDetailMock = mock(ClassDetail.class);

    @BeforeEach
    void init() {
        trainer = new User();
        trainer.setId(1L);
        trainer.setFullName("Test trainer");
        trainer.setEmail("trainer|reniart@gmail.com");

        admin = new ArrayList<>();
        admin1 = new User();
        admin1.setId(2L);
        admin1.setFullName("Test admin 1");
        admin2 = new User();
        admin2.setId(3L);
        admin2.setFullName("Test admin 2");
        admin.add(admin1);
        admin.add(admin2);

        classField = new Class();
        classField.setCode("Java01");
        classField.setName("Test class");
        classField.setDuration(10);

        location = new Location();
        location.setCity("HCM");
        location.setFsu("Ftown1");

        attendee = new Attendee();
        attendee.setType("Intern");
    }

    @Test
    @DisplayName("Test toDTO case 1")
    void toDTOShouldReturnAClassScheduleDTO() {
        //given

        given(classScheduleMock.getClassDetail()).willReturn(classDetailMock);
        given(classScheduleMock.getTrainer()).willReturn(trainer);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(trainer));
        given(classScheduleMock.getStudyDate()).willReturn(LocalDate.of(2023, 3, 13));
        given(classDetailMock.getClassField()).willReturn(classField);
        given(classDetailMock.getAttendee()).willReturn(attendee);
        given(classDetailMock.getLocation()).willReturn(location);
        given(classDetailMock.getStartAt()).willReturn(LocalTime.of(8, 0, 0));
        given(classDetailMock.getFinishAt()).willReturn(LocalTime.of(10, 0, 0));
        given(userRepository.findAdminsOfClass(anyLong())).willReturn(admin);
        given(classScheduleService.getCurrentClassDay(anyLong(), anyLong()))
                .willReturn(4);
        //when
        ClassScheduleDTO result = classScheduleMapper.toDTO(classScheduleMock);
        //then

        assertNotNull(result);
        assertEquals(classField.getCode(), result.getCode());
        assertEquals(classField.getName(), result.getName());
        assertEquals(classField.getDuration(), result.getDuration());
        assertEquals(4, result.getCurrentClassDay());
        assertEquals(location.getCity() + "." + location.getFsu(), result.getLocation());
        assertEquals(trainer.getId(), result.getTrainer().getId());
        assertEquals(2, result.getAdmins().size());
        assertEquals(attendee.getType(), result.getType());
        assertEquals(LocalDate.of(2023, 3, 13), result.getDate());
        assertEquals(LocalTime.of(8, 0, 0), result.getStartAt());
        assertEquals(LocalTime.of(10, 0, 0), result.getFinishAt());

        verify(classScheduleMock).getClassDetail();
        verify(classDetailMock).getClassField();
        verify(classDetailMock).getAttendee();
        verify(userRepository).findAdminsOfClass(anyLong());
        verify(classScheduleService).getCurrentClassDay(anyLong(), anyLong());
        verify(userRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test toDTO case 2")
    void toDTOShouldReturnNull() {
        //given
        ClassSchedule classScheduleMock = mock(ClassSchedule.class);
        ClassDetail classDetailMock = mock(ClassDetail.class);

        given(classScheduleMock.getClassDetail()).willReturn(null);

        //when
        ClassScheduleDTO result = classScheduleMapper.toDTO(classScheduleMock);

        //then
        assertNull(result);

        verify(classScheduleMock).getClassDetail();
        verify(classDetailMock, never()).getClassField();
        verify(classDetailMock, never()).getAttendee();
        verify(userRepository, never()).findAdminsOfClass(anyLong());
        verify(classScheduleService, never()).getCurrentClassDay(anyLong(), anyLong());
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Test toListDTOShould case 1")
    void toListDTOShouldReturnAList() {
        //given

        given(classScheduleMock.getClassDetail()).willReturn(classDetailMock);
        given(classScheduleMock.getTrainer()).willReturn(trainer);
        given(userRepository.findById(anyLong())).willReturn(Optional.of(trainer));
        given(classScheduleMock.getStudyDate()).willReturn(LocalDate.of(2023, 3, 13));
        given(classDetailMock.getClassField()).willReturn(classField);
        given(classDetailMock.getAttendee()).willReturn(attendee);
        given(classDetailMock.getLocation()).willReturn(location);
        given(classDetailMock.getStartAt()).willReturn(LocalTime.of(8, 0, 0));
        given(classDetailMock.getFinishAt()).willReturn(LocalTime.of(10, 0, 0));
        given(userRepository.findAdminsOfClass(anyLong())).willReturn(admin);
        given(classScheduleService.getCurrentClassDay(anyLong(), anyLong()))
                .willReturn(4);
        //when
        List<ClassSchedule> input = new ArrayList<>();
        input.add(classScheduleMock);
        input.add(classScheduleMock);
        input.add(classScheduleMock);
        List<ClassScheduleDTO> result = classScheduleMapper.toListDTO(input);

        //then

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Test toListDTOShould case 2")
    void toListDTOShouldReturnNull() {
        //given

        given(classScheduleMock.getClassDetail()).willReturn(null);

        //when
        List<ClassSchedule> input = new ArrayList<>();
        input.add(classScheduleMock);
        input.add(classScheduleMock);
        input.add(classScheduleMock);
        List<ClassScheduleDTO> result = classScheduleMapper.toListDTO(input);
        //then

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(classScheduleMock, times(3)).getClassDetail();
        verify(classDetailMock, never()).getClassField();
        verify(classDetailMock, never()).getAttendee();
        verify(userRepository, never()).findAdminsOfClass(anyLong());
        verify(classScheduleService, never()).getCurrentClassDay(anyLong(), anyLong());
        verify(userRepository, never()).findById(anyLong());
    }

    @Test
    @DisplayName("Test toReturnUserDTO case 1")
    void toReturnUserDTOShouldReturnAReturnDTO() {
        //given
        User user = new User();
        user.setId(1L);
        user.setFullName("ABC DEf");
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        //when
        ReturnUserDTO result = classScheduleMapper.toReturnUserDTO(anyLong());

        //then
        assertEquals(1L, result.getId());
        assertEquals("ABC DEf", result.getName());
        verify(userRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Test toReturnUserDTO case 2")
    void toReturnUserDTOShouldReturnNull() {
        //given
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        ReturnUserDTO result = classScheduleMapper.toReturnUserDTO(anyLong());

        //then
        assertNull(result);
        verify(userRepository).findById(anyLong());
    }
}