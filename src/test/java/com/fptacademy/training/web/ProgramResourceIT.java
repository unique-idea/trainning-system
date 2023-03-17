package com.fptacademy.training.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fptacademy.training.IntegrationTest;
import com.fptacademy.training.domain.Program;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.repository.ProgramRepository;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.security.jwt.JwtTokenProvider;
import com.fptacademy.training.web.vm.ProgramVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@AutoConfigureMockMvc
@IntegrationTest
public class ProgramResourceIT {
    private final String username = "admin";
    private final String password = "12345";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    private String accessToken;
    @BeforeEach
    public void init() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username, password, List.of(new SimpleGrantedAuthority(Permissions.PROGRAM_VIEW)));
        accessToken = tokenProvider.generateAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testCreateProgram() throws Exception {
        List<Syllabus> syllabuses = List.of(new Syllabus(), new Syllabus(), new Syllabus());
        syllabusRepository.saveAll(syllabuses);
        ProgramVM programVM = new ProgramVM("Program test", syllabusRepository.findAll().stream().map(Syllabus::getId).toList());
        Program program = new Program();
        mockMvc.perform(post("/api/programs")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(program)))
                .andExpect(status().isCreated());
        assertThat(programRepository.existsByName("Program test")).isTrue();
    }
}
