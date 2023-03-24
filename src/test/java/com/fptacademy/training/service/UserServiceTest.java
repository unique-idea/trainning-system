package com.fptacademy.training.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.mockito.stubbing.Answer;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.service.dto.UserDto;
import com.fptacademy.training.web.vm.NoNullRequiredUserVM;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.fptacademy.training.domain.enumeration.UserStatus;
import com.fptacademy.training.exception.ResourceBadRequestException;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.service.mapper.UserMapper;
import com.fptacademy.training.service.util.ExcelUploadService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
@Slf4j
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

    @Test
    void whenCorrectInputIntoUpdateUserByIdInUserService_methodShouldFindByIdFirstThenSaveWithBothExactlyValueGiven() {
        //Declare
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);

        InOrder inOrder = Mockito.inOrder(userRepository);
        Random rd = new Random();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        String fullName = rd.nextBoolean() ? "Tran Huu Tri1" : null,
                email = rd.nextBoolean() ? "tri1@gmail.com" : null,
                code = rd.nextBoolean() ? "12345" : null,
                level = rd.nextBoolean() ? "Basic" : null,
                avatarUrl = rd.nextBoolean() ? "http://image//img1" : null,
                status = rd.nextBoolean() ? "active" : null,
                birthDay = rd.nextBoolean() ? "2003-04-13" : null,
                gender = rd.nextBoolean() ? "male" : null,
                activated = rd.nextBoolean() ? "active" : null;
        //Value Given
        String fullNameGiven = fullName == null ? "Tran Huu Tri" : fullName,
                emailGiven = email == null ? "tri@gmail.com" : email,
                codeGiven = code == null ? "1234" : code,
                avatarUrlGiven = avatarUrl == null ? "http://image" : avatarUrl,
                passwordGiven = "12345";

        Boolean genderGiven = gender == null ? false : true,
                activatedGiven = activated == null ? false : true;

        Level levelGiven = level == null ? Level.builder().id(2L).name("Intermediate").build() : Level.builder().id(1L).name(level).build();
        LocalDate birthDayGiven = birthDay == null ? LocalDate.parse("2002-04-13") : LocalDate.parse(birthDay);
        Role roleGiven = Role.builder().id(2L).name("Class Admin").build();
        UserStatus userStatusGiven = status == null ? UserStatus.INACTIVE : UserStatus.ACTIVE;
        Long idGiven = 2L;

        User userGiven = User.builder().
                id(idGiven).
                code(codeGiven).
                fullName(fullNameGiven).
                email(emailGiven).
                password(bCryptPasswordEncoder.encode(passwordGiven)).
                birthday(birthDayGiven).
                gender(genderGiven).
                activated(activatedGiven).
                avatarUrl(avatarUrlGiven).
                level(levelGiven).
                role(roleGiven).
                status(userStatusGiven).
                build();

        NoNullRequiredUserVM nUserVmGiven = new NoNullRequiredUserVM(fullName, email, birthDay, gender, activated,
                level, null, status, avatarUrl, code);

        //Call
        when(userRepository.findById(any())).thenReturn(Optional.of(userGiven));
        when(userRepository.save(any())).thenReturn(userGiven);

        userService.updateUserById(nUserVmGiven, idGiven);
        //Testing
        verify(userRepository).findById(idCapture.capture());
        verify(userRepository).save(userCapture.capture());

        inOrder.verify(userRepository).findById(any());
        inOrder.verify(userRepository).save(any());

        assertEquals(idGiven, idCapture.getValue());
        assertEquals(userGiven.getId(), idCapture.getValue());
        assertEquals(userGiven.getFullName(), userCapture.getValue().getFullName());
        assertEquals(userGiven.getActivated(), userCapture.getValue().getActivated());
        assertEquals(userGiven.getCode(), userCapture.getValue().getCode());
        assertEquals(userGiven.getBirthday(), userCapture.getValue().getBirthday());
        assertEquals(userGiven.getGender(), userCapture.getValue().getGender());
        assertEquals(userGiven.getEmail(), userCapture.getValue().getEmail());
        assertEquals(userGiven.getLevel(), userCapture.getValue().getLevel());
        assertEquals(userGiven.getStatus(), userCapture.getValue().getStatus());
        assertEquals(userGiven.getRole(), userCapture.getValue().getRole());
        assertEquals(userGiven.getAvatarUrl(), userCapture.getValue().getAvatarUrl());
        assertEquals(userGiven.getPassword(), userCapture.getValue().getPassword());
    }

    @Test
    void whenNullInputIntoUpdateUserInUserService_thenReturnExceptionRelatedToIt() {
        NoNullRequiredUserVM nUserVm = new NoNullRequiredUserVM("value", null, null, null, null, null,
                null, null, null, null);
        Long id = 2L;

        assertThatThrownBy(() -> userService.updateUserById(null, id))
                .isInstanceOf(ResourceBadRequestException.class)
                .hasMessageContaining("Invalid params");

        assertThatThrownBy(() -> userService.updateUserById(nUserVm, null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Can't update not existed user!");

        assertThatThrownBy(() -> userService.updateUserById(null, null))
                .isInstanceOf(ResourceBadRequestException.class)
                .hasMessageContaining("Invalid params");
    }

    @Test
    void whenCorrectInputIdIntoGetUserByIdInUserService_thenFindByIdMustCall() {
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        Long idGiven = 2L;
        User user = User.builder()
                .id(idGiven).
                fullName("Nguyen Dinh Tien").
                email("tienndse@fpt.edu.vn").
                code("SE16000").
                password("SE16000").
                gender(Boolean.TRUE).
                role(Role.builder().name("Trainer").build()).
                activated(Boolean.TRUE).
                level(Level.builder().name("AA").build()).
                status(UserStatus.ACTIVE).
                avatarUrl("image.com").
                build();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.getUserById(idGiven);

        verify(userRepository).findById(idCapture.capture());
        assertEquals(idGiven, idCapture.getValue());
    }

    @Test
    void whenNullOrIncorrectIdInputIntoGetUserByIdInUserService_thenReturnAnException(){
        Long overId = Long.MAX_VALUE;
        assertThatThrownBy(() -> userService.getUserById(null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not existed!");

        assertThatThrownBy(() -> userService.getUserById(overId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User not existed!");

    }
}
