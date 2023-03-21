package com.fptacademy.training.web;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.domain.enumeration.UserStatus;
import com.fptacademy.training.service.UserService;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.service.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    //    @Spy
    @InjectMocks
    private UserResourceImpl controller;

    @BeforeEach
    public void getAccessToken() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    void testUploadExcelFile() throws Exception {
        InputStream inputStream = getClass().getResourceAsStream("templates/User-Test.xlsx");
        MockMultipartFile file = new MockMultipartFile("file", "User-test.xlsx", MediaType.APPLICATION_OCTET_STREAM_VALUE, inputStream);

        User user = User.builder().
                fullName("Nguyen Dinh Tien").
                email("tienndse@fpt.edu.vn").
                code("SE16000").
                password("SE16000").
                gender(Boolean.TRUE).
                role(Role.builder().name("Trainer").build()).
                activated(Boolean.TRUE).
                level(Level.builder().name("AA").build()).
                status(UserStatus.ACTIVE).
                avatarUrl("image.com")
                .build();
        User user1 = User.builder().
                fullName("A Nguyen").
                email("an@fpt.edu.vn").
                code("SE16001").
                password("SE16001").
                gender(Boolean.TRUE).
                role(Role.builder().name("Trainer").build()).
                activated(Boolean.TRUE).
                level(Level.builder().name("AA").build()).
                status(UserStatus.ACTIVE).
                avatarUrl("image.com")
                .build();
        List<User> userList = new ArrayList<>(Arrays.asList(user, user1));
        List<UserDto> dto = userMapper.toDtos(userList);
        when(userService.importUsersToDB(file)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/api/user/import")
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
