package com.fptacademy.training.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.service.ClassScheduleService;
import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.ClassScheduleReturnDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ClassScheduleResourceImplTest {

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

    //    @Autowired
//    private Jackson2ObjectMapperBuilder mapperBuilder;
    private static final String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJhdXRoIjoiQ2xhc3NfRnVsbEFjY2VzcyxNYXRlcmlhbF9GdWxsQWNjZXNzLFByb2dyYW1fRnVsbEFjY2VzcyxTeWxsYWJ1c19GdWxsQWNjZXNzLFVzZXJfRnVsbEFjY2VzcyIsImV4cCI6MTY3ODk1OTcyNn0.SaWGkjSJW0iPbhJrsMqgu162GN3Y7cVfEMRkBQBiCfw";
    private List<ClassScheduleReturnDto> classScheduleDTOList;
    private ClassScheduleReturnDto classScheduleDTO1;
    private ClassScheduleReturnDto classScheduleDTO2;
    private User user1;


    @BeforeEach
    void setUp() {
        classScheduleDTO1 = new ClassScheduleReturnDto();
        classScheduleDTO1.setCode("Java01");
        classScheduleDTO1.setName("Java intern 01");
        classScheduleDTO1.setDate(LocalDate.now());
        classScheduleDTO1.setType("Intern");
        classScheduleDTO1.setLocation("HCM.Ftown1");

        classScheduleDTO2 = new ClassScheduleReturnDto();
        classScheduleDTO2.setCode("React01");
        classScheduleDTO2.setName("React intern 01");
        classScheduleDTO2.setDate(LocalDate.now());
        classScheduleDTO2.setType("Intern");
        classScheduleDTO2.setLocation("HCM.Ftown1");

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
        given(classScheduleMapper.toListDTO(anyList()))
                .willReturn(classScheduleDTOList);
        ObjectMapper om = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/calendar/{day}", day)
//                        .header("Authorization", token)
//                        .header("Content-Type", "application/json")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].code").value("Java01"))
                .andExpect(jsonPath("$", hasSize(2)));
        verify(userService).getCurrentUserLogin();
        verify(classScheduleService).getClassScheduleOfAUserByDate(any(LocalDate.class), anyLong());
        verify(classScheduleMapper).toListDTO(anyList());
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