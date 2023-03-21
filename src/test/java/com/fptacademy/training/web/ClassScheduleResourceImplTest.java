package com.fptacademy.training.web;

import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.ClassScheduleService;
import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.ReturnClassScheduleDto;
import com.fptacademy.training.service.dto.ReturnUnitDto;
import com.fptacademy.training.service.mapper.ClassScheduleMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClassScheduleResourceImplTest {

    //    @Autowired
//    private Jackson2ObjectMapperBuilder mapperBuilder;
    private static final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJhdXRoIjoiQ2xhc3NfRnVsbEFjY2VzcyxNYXRlcmlhbF9GdWxsQWNjZXNzLFByb2dyYW1fRnVsbEFjY2VzcyxTeWxsYWJ1c19GdWxsQWNjZXNzLFVzZXJfRnVsbEFjY2VzcyIsImV4cCI6MTY3ODk1OTcyNn0.SaWGkjSJW0iPbhJrsMqgu162GN3Y7cVfEMRkBQBiCfw";
    @Mock
    private ClassScheduleService classScheduleService;
    @Mock
    private ClassScheduleMapper classScheduleMapper;
    @Mock
    private UserService userService;
    @InjectMocks
    private ClassScheduleResourceImpl classScheduleResource;
    @Autowired
    private MockMvc mockMvc;
    private List<ReturnClassScheduleDto> classScheduleDTOList;
    private ReturnClassScheduleDto classScheduleDTO1;
    private ReturnClassScheduleDto classScheduleDTO2;
    private ReturnUnitDto unitDto1;
    private ReturnUnitDto unitDto2;
    private List<ReturnUnitDto> unitDtos;
    private User user1;


    @BeforeEach
    void setUp() {
        unitDto1 = new ReturnUnitDto(1L, 1, "Test unit 1", "Test unit 1");
        unitDto2 = new ReturnUnitDto(2L, 2, "Test unit 2", "Test unit 2");
        unitDtos = new ArrayList<>();
        unitDtos.add(unitDto1);
        unitDtos.add(unitDto2);

        classScheduleDTO1 = new ReturnClassScheduleDto();
        classScheduleDTO1.setClassId(1L);
        classScheduleDTO1.setClassCode("Java01");
        classScheduleDTO1.setClassName("Java intern 01");
        classScheduleDTO1.setDate(LocalDate.now());
        classScheduleDTO1.setType("Intern");
        classScheduleDTO1.setCity("Ho Chi Minh");
        classScheduleDTO1.setFsu("Ftown1");
        classScheduleDTO1.setUnits(unitDtos);

        classScheduleDTO2 = new ReturnClassScheduleDto();
        classScheduleDTO2.setClassId(2L);
        classScheduleDTO2.setClassCode("React01");
        classScheduleDTO2.setClassName("React intern 01");
        classScheduleDTO2.setDate(LocalDate.now());
        classScheduleDTO2.setType("Intern");
        classScheduleDTO1.setCity("Ho Chi Minh");
        classScheduleDTO1.setFsu("Ftown3");
        classScheduleDTO1.setUnits(unitDtos);

        classScheduleDTOList = new ArrayList<>();
        classScheduleDTOList.add(classScheduleDTO1);
        classScheduleDTOList.add(classScheduleDTO2);

        user1 = new User();
        user1.setId(1L);
        user1.setFullName("Test user1");

        mockMvc = MockMvcBuilders.standaloneSetup(classScheduleResource).build();
    }

    @Test
    void getAllClassScheduleShouldReturnAListOfClassScheduleDTO() throws Exception {
        LocalDate day = LocalDate.now();
        given(userService.getCurrentUserLogin()).willReturn(user1);
        given(classScheduleService.getClassScheduleOfAUserByDate(any(LocalDate.class), anyLong()))
                .willReturn(new ArrayList<>());
        given(classScheduleMapper.toListReturnClassScheduleDto(anyList()))
                .willReturn(classScheduleDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/calendar/{day}", day)
//                        .header("Authorization", token)
//                        .header("Content-Type", "application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].classCode").value("Java01"))
                .andExpect(jsonPath("$[1].classCode").value("React01"))
                .andExpect(jsonPath("$[0].classId").value("1"))
                .andExpect(jsonPath("$[1].classId").value("2"))
                .andExpect(jsonPath("$[0].className").value("Java intern 01"))
                .andExpect(jsonPath("$[1].className").value("React intern 01"))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(userService).getCurrentUserLogin();
        verify(classScheduleService).getClassScheduleOfAUserByDate(any(LocalDate.class), anyLong());
        verify(classScheduleMapper).toListReturnClassScheduleDto(anyList());
    }

    @Test
    void getAllClassScheduleByWeek() {
    }

    @Test
    void getAllClassScheduleOfCurrentUser() {
    }

    @Test
    void getAllClassScheduleOfCurrentUserByWeek() {
    }
}