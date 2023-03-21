package com.fptacademy.training.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fptacademy.training.IntegrationTest;
import com.fptacademy.training.domain.*;
import com.fptacademy.training.repository.*;
import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.security.jwt.JwtTokenProvider;
import com.fptacademy.training.web.vm.ProgramVM;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AutoConfigureMockMvc
@IntegrationTest
public class ProgramResourceIT {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private MockMvc mockMvc;
    private final String DEFAULT_PROGRAM_NAME = "Test Program";
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    private String accessToken;
    @Autowired
    private ProgramRepository programRepository;

    @BeforeEach
    @Transactional
    void setup() {
        Role role = TestUtil.getRole(List.of(Permissions.PROGRAM_FULL_ACCESS));
        roleRepository.saveAndFlush(role);
        User user = TestUtil.getUser(role);
        userRepository.saveAndFlush(user);
        Authentication authentication = TestUtil.createAuthentication(user);
        accessToken = tokenProvider.generateAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Syllabus syllabus = new Syllabus();
        syllabus.setDuration(1);
        Session session = new Session();
        session.setSyllabus(syllabus);
        Unit unit = new Unit();
        unit.setSession(session);
        unit.setTotalDurationLesson(12.5);
        session.setUnits(List.of(unit));
        List<Syllabus> syllabuses = List.of(syllabus);
        syllabusRepository.saveAllAndFlush(syllabuses);
    }
    @AfterEach
    @Transactional
    void teardown() {
        programRepository.deleteAll();
        syllabusRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
    @Test
    void testCreateProgram() throws Exception {
        List<Long> syllabusIds = syllabusRepository.findAll().stream().mapToLong(Syllabus::getId).boxed().toList();
        ProgramVM programVM = new ProgramVM(DEFAULT_PROGRAM_NAME, syllabusIds);
        mockMvc
                .perform(post("/api/programs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(programVM))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    void testCreateProgramWithConflictName() throws Exception {
        Program program = new Program();
        program.setName(DEFAULT_PROGRAM_NAME);
        programRepository.saveAndFlush(program);
        ProgramVM programVM = new ProgramVM(DEFAULT_PROGRAM_NAME, List.of());
        mockMvc
                .perform(post("/api/programs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(programVM))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isConflict());
    }

    @Test
    void testCreateProgramWithNonExistSyllabuses() throws Exception {
        ProgramVM programVM = new ProgramVM(DEFAULT_PROGRAM_NAME, List.of(99L, 100L, 101L));
        mockMvc
                .perform(post("/api/programs")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(TestUtil.convertObjectToJsonBytes(programVM))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }
}
