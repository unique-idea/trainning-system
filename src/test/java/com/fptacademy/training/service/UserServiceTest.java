package com.fptacademy.training.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.time.LocalDate;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fptacademy.training.domain.UserStatus;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.mapper.UserMapper;
import com.fptacademy.training.service.util.ExcelUploadService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private LevelService levelService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private ExcelUploadService excelUploadService;

    private UserService userService;

    AutoCloseable autoClosable;

    @BeforeEach
    void setUp() {
        autoClosable = openMocks(this);
        userService = new UserService(userRepository, roleService, levelService, userMapper, excelUploadService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoClosable.close();
    }

    @Test
    void shouldCalledFindByFiltersCorrect_whenUseGetUsersByFiltersService() {
        Random rd = new Random();

        ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class),
                fullNameCaptor = ArgumentCaptor.forClass(String.class),
                codeCaptor = ArgumentCaptor.forClass(String.class),
                levelNameCaptor = ArgumentCaptor.forClass(String.class),
                roleNameCaptor = ArgumentCaptor.forClass(String.class),
                statusCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Boolean> activatedCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<LocalDate> birthdayFromCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<LocalDate> birthdayToCaptor = ArgumentCaptor.forClass(LocalDate.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        final String email = rd.nextBoolean() ? "Gmail" : null,
                fullName = rd.nextBoolean() ? "Nguyen Van A" : null,
                code = rd.nextBoolean() ? "USER001" : null,
                levelName = rd.nextBoolean() ? "Beginner" : null,
                roleName = rd.nextBoolean() ? "ROLE_USER" : null,
                status = rd.nextBoolean() ? UserStatus.ACTIVE.toString() : null,
                birthdayFrom = rd.nextBoolean() ? "1999-01-01" : null,
                birthdayTo = rd.nextBoolean() ? "2023-01-01" : null;
        final Boolean activated = false;
        final LocalDate birthdayFromDate = birthdayFrom != null ? LocalDate.parse(birthdayFrom) : LocalDate.of(0, 1, 1);
        final LocalDate birthdayToDate = birthdayTo != null ? LocalDate.parse(birthdayTo) : LocalDate.of(9999, 12, 31);
        final String statusName = status != null ? status.replace(" ", "_") : null;
        final String sort = "id,asc";
        final int page = 0;
        final int size = 10;
        final Direction sortDirection = Direction.valueOf(sort.split(",")[1].toUpperCase());
        final String sortProperty = sort.split(",")[0];
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortProperty));

        when(userRepository.findByFilters(any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Page.empty());
        userService.getUsersByFilters(email, fullName, code, levelName, roleName, activated, birthdayFrom, birthdayTo,
                status, "id,asc", 0, 10);

        verify(userRepository).findByFilters(emailCaptor.capture(), fullNameCaptor.capture(),
                codeCaptor.capture(), levelNameCaptor.capture(),
                roleNameCaptor.capture(), activatedCaptor.capture(),
                birthdayFromCaptor.capture(), birthdayToCaptor.capture(),
                statusCaptor.capture(), pageableCaptor.capture());

        assertEquals(email, emailCaptor.getValue());
        assertEquals(fullName, fullNameCaptor.getValue());
        assertEquals(code, codeCaptor.getValue());
        assertEquals(levelName, levelNameCaptor.getValue());
        assertEquals(roleName, roleNameCaptor.getValue());
        assertEquals(activated, activatedCaptor.getValue());
        assertEquals(birthdayFromDate, birthdayFromCaptor.getValue());
        assertEquals(birthdayToDate, birthdayToCaptor.getValue());
        assertEquals(statusName, statusCaptor.getValue());
        assertEquals(pageable, pageableCaptor.getValue());
    }

    @Test
    void shouldThrowException_whenWrongDateFormat() {
        String birthdayWrongFormat = "01-01-2001";

        assertThatThrownBy(() -> userService.getUsersByFilters(null, null, null, null, null, null,
                birthdayWrongFormat, null, null, "id,asc", 0, 1))
                .isInstanceOf(ResourceBadRequestException.class)
                .hasMessageContaining(birthdayWrongFormat + ": Date format is wrong. Please use yyyy-MM-dd format");
        assertThatThrownBy(() -> userService.getUsersByFilters(null, null, null, null, null, null,
                null, birthdayWrongFormat, null, "id,asc", 0, 1))
                .isInstanceOf(ResourceBadRequestException.class)
                .hasMessageContaining(birthdayWrongFormat + ": Date format is wrong. Please use yyyy-MM-dd format");
    }

    @Test
    void shouldThrowException_whenWrongSortPropertyAndSortDirection() {
        final String sortWrongProperty = "idd,asc";
        assertThatThrownBy(() -> userService.getUsersByFilters(null, null, null, null, null, null,
                null, null, null, sortWrongProperty, 0, 1))
                .isInstanceOf(ResourceBadRequestException.class)
                .hasMessageContaining(sortWrongProperty.split(",")[0] + ": Sort property is not valid");

        final String sortWrongDirection = "id,assc";
        assertThatThrownBy(() -> userService.getUsersByFilters(null, null, null, null, null, null,
                null, null, null, sortWrongDirection, 0, 1))
                .isInstanceOf(ResourceBadRequestException.class)
                .hasMessageContaining(sortWrongDirection.split(",")[1].toUpperCase() + ": Sort direction must be ASC or DESC");
    }
}
