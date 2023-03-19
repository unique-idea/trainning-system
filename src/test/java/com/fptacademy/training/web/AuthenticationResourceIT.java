package com.fptacademy.training.web;

import com.fptacademy.training.IntegrationTest;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.security.jwt.JwtTokenProvider;
import com.fptacademy.training.web.vm.LoginVM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@IntegrationTest
public class AuthenticationResourceIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoleRepository roleRepository;
    final String EMAIL = "test@gmail.com";
    final String NAME = "Just A Test";
    final String CODE = "TEST123";
    final String AVATAR_URL = "https://image.com/test.jpg";
    final String PASSWORD = "test_password";

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        Role role = new Role();
        role.setName("Test");
        role.setPermissions(List.of(Permissions.CLASS_CREATE));
        roleRepository.saveAndFlush(role);

        User user = new User();
        user.setEmail(EMAIL);
        user.setFullName(NAME);
        user.setCode(CODE);
        user.setAvatarUrl(AVATAR_URL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setActivated(true);
        user.setRole(role);
        userRepository.saveAndFlush(user);
    }

    @Test
    @Transactional
    void testLogin() throws Exception {
        LoginVM loginVM = new LoginVM(EMAIL, PASSWORD);

        mockMvc
                .perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(loginVM)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value(NAME))
                .andExpect(jsonPath("$.avatarUrl").value(AVATAR_URL))
                .andExpect(jsonPath("$.tokens.accessToken").isString())
                .andExpect(jsonPath("$.tokens.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.tokens.refreshToken").isString())
                .andExpect(jsonPath("$.tokens.refreshToken").isNotEmpty());
    }

    @Test
    void testLoginFails() throws Exception {
        LoginVM loginVM = new LoginVM("wrong-email", "wrong-password");
        mockMvc
                .perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(loginVM)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void testGetAccessTokenFromRefreshToken() throws Exception {
        String refreshToken = tokenProvider.generateRefreshToken("test@gmail.com");
        mockMvc
                .perform(get("/api/auth/refresh").header("Refresh-Token", refreshToken))
                .andExpect(jsonPath("$.accessToken").isString())
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.refreshToken").value(refreshToken));
    }

    @Test
    void testGetAccessTokenFromRefreshTokenFails() throws Exception {
        String refreshToken = "invalid-token";
        mockMvc
                .perform(get("/api/auth/refresh").header("Refresh-Token", refreshToken))
                .andExpect(status().isUnauthorized());
    }
}
