package com.fptacademy.training.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fptacademy.training.IntegrationTest;
import com.fptacademy.training.domain.*;
import com.fptacademy.training.factory.ProgramFactory;
import com.fptacademy.training.factory.RoleFactory;
import com.fptacademy.training.factory.SyllabusFactory;
import com.fptacademy.training.factory.UserFactory;
import com.fptacademy.training.repository.*;
import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.security.jwt.JwtTokenProvider;
import com.fptacademy.training.web.vm.ProgramVM;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AutoConfigureMockMvc
@IntegrationTest
public class ProgramResourceIT {
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private ProgramRepository programRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private MockMvc mockMvc;
    private String accessToken;
    private final String DEFAULT_PROGRAM_NAME = "Test Program";

    @BeforeEach
    @Transactional
    void setup() {
        Role role = RoleFactory.createRoleWithPermissions(Permissions.PROGRAM_FULL_ACCESS);
        roleRepository.saveAndFlush(role);
        User user = UserFactory.createActiveUser(role);
        userRepository.saveAndFlush(user);
        Authentication authentication = TestUtil.createAuthentication(user);
        accessToken = tokenProvider.generateAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @AfterEach
    @Transactional
    void teardown() {
        SecurityContextHolder.clearContext();
        programRepository.deleteAll();
        syllabusRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
    @Test
    @Transactional
    void testCreateProgram() throws Exception {
        List<Syllabus> syllabuses = List.of(
                SyllabusFactory.createDummySyllabus(),
                SyllabusFactory.createDummySyllabus());
        syllabusRepository.saveAllAndFlush(syllabuses);
        SecurityContextHolder.clearContext();
        List<Long> syllabusIds = syllabuses.stream().mapToLong(Syllabus::getId).boxed().toList();
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
        Program program = Program.builder().name(DEFAULT_PROGRAM_NAME).build();
        programRepository.saveAndFlush(program);
        SecurityContextHolder.clearContext();
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

    @Test
    @Transactional
    void testGetProgramListWithPagination() throws Exception {
        for (int i = 0; i < 20; ++i) {
            Program program = ProgramFactory.createDummyProgram();
            syllabusRepository.saveAllAndFlush(program.getSyllabuses());
            programRepository.saveAndFlush(program);
        }
        SecurityContextHolder.clearContext();
        mockMvc
                .perform(get("/api/programs")
                        .param("page", "2")
                        .param("size", "10")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(20))
                .andExpect(jsonPath("$.programs").isArray())
                .andExpect(jsonPath("$.programs", Matchers.hasSize(10)))
                .andExpect(jsonPath("$.programs[0].id").value(11));

        mockMvc
                .perform(get("/api/programs")
                        .param("sort", "id,desc")
                        .param("page", "2")
                        .param("size", "10")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(20))
                .andExpect(jsonPath("$.programs").isArray())
                .andExpect(jsonPath("$.programs", Matchers.hasSize(10)))
                .andExpect(jsonPath("$.programs[0].id").value(10));
    }

    @Test
    void testDownloadProgramExcelTemplate() throws Exception {
        SecurityContextHolder.clearContext();
        MvcResult mvcResult = mockMvc
                .perform(get("/api/programs/import/template")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();
        Assertions.assertThat(mvcResult.getResponse().getContentType())
                .isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @Test
    @Transactional
    public void testDeleteProgram() throws Exception {
        Program program = ProgramFactory.createDummyProgram();
        syllabusRepository.saveAllAndFlush(program.getSyllabuses());
        programRepository.saveAndFlush(program);
        SecurityContextHolder.clearContext();
        mockMvc.perform(delete("/api/programs/{id}", program.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProgramNotFound() throws Exception {
        mockMvc.perform(delete("/api/programs/{id}", 999)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().isNotFound());
    }
}