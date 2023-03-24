package com.fptacademy.training.service;

import com.fptacademy.training.domain.Program;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.Syllabus;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.exception.ResourceAlreadyExistsException;
import com.fptacademy.training.factory.ProgramFactory;
import com.fptacademy.training.factory.RoleFactory;
import com.fptacademy.training.factory.SyllabusFactory;
import com.fptacademy.training.factory.UserFactory;
import com.fptacademy.training.repository.ProgramRepository;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.repository.SyllabusRepository;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.service.dto.ProgramDto;
import com.fptacademy.training.web.TestUtil;
import com.fptacademy.training.web.vm.ProgramVM;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProgramServiceTest {
    @Autowired
    private ProgramService programService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SyllabusRepository syllabusRepository;
    @Autowired
    private ProgramRepository programRepository;
    private final String DEFAULT_PROGRAM_NAME = "Test Program";
    private User user;

    @BeforeEach
    void setup() {
        Role role = RoleFactory.createRoleWithPermissions(Permissions.PROGRAM_FULL_ACCESS);
        roleRepository.saveAndFlush(role);
        user = UserFactory.createActiveUser(role);
        userRepository.saveAndFlush(user);
        Authentication authentication = TestUtil.createAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @AfterEach
    void teardown() {
        SecurityContextHolder.clearContext();
        programRepository.deleteAll();
        syllabusRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }
    @Test
    void testCreateProgram() {
        List<Syllabus> syllabuses = List.of(SyllabusFactory.createDummySyllabus(),
                SyllabusFactory.createDummySyllabus());
        syllabusRepository.saveAllAndFlush(syllabuses);
        ProgramVM programVM = new ProgramVM(DEFAULT_PROGRAM_NAME, syllabuses.stream().map(Syllabus::getId).toList());
        ProgramDto programDTO = programService.createProgram(programVM);
        assertThat(programDTO).isNotNull();
        assertThat(programDTO.getName()).isEqualTo(DEFAULT_PROGRAM_NAME);
        assertThat(programDTO.getCreatedBy().getId()).isEqualTo(user.getId());
        assertThat(programDTO.getLastModifiedBy().getId()).isEqualTo(user.getId());
        assertThat(programDTO.getDurationInDays()).isEqualTo(syllabuses.stream().mapToInt(Syllabus::getDuration).sum());
    }

    @Test
    void testCreateProgramWithExistedName() {
        Program program = ProgramFactory.createDummyProgram();
        program.setName(DEFAULT_PROGRAM_NAME);
        syllabusRepository.saveAllAndFlush(program.getSyllabuses());
        programRepository.saveAndFlush(program);
        List<Syllabus> syllabuses = List.of(SyllabusFactory.createDummySyllabus(),
                SyllabusFactory.createDummySyllabus());
        syllabusRepository.saveAllAndFlush(syllabuses);
        ProgramVM programVM = new ProgramVM(DEFAULT_PROGRAM_NAME, syllabuses.stream().map(Syllabus::getId).toList());
        assertThatExceptionOfType(ResourceAlreadyExistsException.class)
                .isThrownBy(() -> programService.createProgram(programVM));
    }
}