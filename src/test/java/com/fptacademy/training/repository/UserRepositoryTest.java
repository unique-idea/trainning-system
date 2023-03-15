package com.fptacademy.training.repository;

import com.fptacademy.training.domain.*;
import com.fptacademy.training.domain.Class;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ClassDetailRepository classDetailRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private ClassScheduleRepository classScheduleRepository;

    private User user1, user2, user3, user4, user5;
    private Class classFields;
    private ClassDetail classDetail1, classDetail2;

    private ClassSchedule classSchedule1, classSchedule2, classSchedule3, classSchedule4, classSchedule5;

    @BeforeEach
    void setUp() {

        classScheduleRepository.deleteAll();
        classDetailRepository.deleteAll();
        attendeeRepository.deleteAll();
        classRepository.deleteAll();
        userRepository.deleteAll();
        levelRepository.deleteAll();
        roleRepository.deleteAll();
        SecurityContextHolder.clearContext();

        String password = passwordEncoder.encode("12345");

        List<String> permission = new ArrayList<>();
        permission.add("Class_FullAccess");
        permission.add("User_FullAccess");

        Role role = new Role();
        role.setName("Super Admin");
        role.setPermissions(permission);
        role = roleRepository.save(role);

        Role role1 = new Role();
        role1.setName("Class Admin");
        role1.setPermissions(permission);
        role1 = roleRepository.save(role1);

        Role role2 = new Role();
        role2.setName("Attendee");
        role2.setPermissions(permission);
        role2 = roleRepository.save(role2);

        Level level1 = new Level();
        level1.setName("Intern");
        levelRepository.save(level1);

        Level level2 = new Level();
        level2.setName("Fresher");
        level2 = levelRepository.save(level2);

        Location location = new Location();
        location.setCity("Ho Chi Minh");
        location.setFsu("Ftown1");
        locationRepository.save(location);

        Location location1 = new Location();
        location1.setCity("Ho Chi Minh");
        location1.setFsu("Ftown2");
        locationRepository.save(location1);

        Location location2 = new Location();
        location2.setCity("Ha Noi");
        location2.setFsu("Ftown2");
        locationRepository.save(location2);

        Attendee attendee1 = new Attendee();
        attendee1.setType("Intern");
        attendeeRepository.save(attendee1);

        Attendee attendee2 = new Attendee();
        attendee2.setType("Fresher");
        attendeeRepository.save(attendee2);


        user1 = new User();
        user1.setCode("user1");
        user1.setFullName("User 1");
        user1.setEmail("user1@test.com");
        user1.setPassword(password);
        user1.setBirthday(LocalDate.of(1990, 1, 1));
        user1.setGender(true);
        user1.setActivated(true);
        user1.setRole(role);
        user1.setLevel(level1);
        user1.setAvatarUrl("https://avatar.url/user1");
        user1 = userRepository.save(user1);


        user2 = new User();
        user2.setCode("user2");
        user2.setFullName("User 2");
        user2.setEmail("user2@test.com");
        user2.setPassword(password);
        user2.setBirthday(LocalDate.of(1990, 2, 2));
        user2.setGender(false);
        user2.setActivated(true);
        user2.setRole(role1);
        user2.setLevel(level1);
        user2.setAvatarUrl("https://avatar.url/user2");
        user2 = userRepository.save(user2);


        user3 = new User();
        user3.setCode("user3");
        user3.setFullName("User 3");
        user3.setEmail("user3@test.com");
        user3.setPassword(password);
        user3.setBirthday(LocalDate.of(1990, 3, 3));
        user3.setGender(true);
        user3.setActivated(false);
        user3.setRole(role2);
        user3.setLevel(level1);
        user3.setAvatarUrl("https://avatar.url/user3");
        user3 = userRepository.save(user3);


        user4 = new User();
        user4.setCode("user4");
        user4.setFullName("User 4");
        user4.setEmail("user4@test.com");
        user4.setPassword(password);
        user4.setBirthday(LocalDate.of(1990, 4, 4));
        user4.setGender(false);
        user4.setActivated(true);
        user4.setRole(role2);
        user4.setLevel(level1);
        user4.setAvatarUrl("https://avatar.url/user4");
        user4 = userRepository.save(user4);


        user5 = new User();
        user5.setCode("user5");
        user5.setFullName("User 5");
        user5.setEmail("user5@test.com");
        user5.setPassword(password);
        user5.setBirthday(LocalDate.of(1990, 5, 5));
        user5.setGender(true);
        user5.setActivated(true);
        user5.setRole(role2);
        user5.setLevel(level1);
        user5.setAvatarUrl("https://avatar.url/user5");
        user5 = userRepository.save(user5);

        //Set login user
        User currentUser = userRepository.findById(user1.getId()).orElse(null);

        Collection<? extends GrantedAuthority> authorities = Arrays.
                stream(currentUser.getRole().getPermissions().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                currentUser.getEmail(),
                null,  //credential
                authorities// authorities
        );

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        //set login user


        classFields = new Class();
        classFields.setName("Class 1");
        classFields.setCode("C1");
        classFields.setCreatedAt(Instant.now());
        classFields.setCreatedBy(userRepository.findById(user1.getId()).orElse(null));
        classFields.setDuration(4);
        classFields.setLastModifiedAt(Instant.now());
        classFields.setLastModifiedBy(userRepository.findById(user1.getId()).orElse(null));
        classFields = classRepository.save(classFields);


        classDetail1 = new ClassDetail();
        classDetail1.setClassField(classFields);
        classDetail1.setAccepted(20);
        classDetail1.setPlanned(20);
        classDetail1.setActual(20);
        classDetail1.setStartAt(LocalTime.of(8, 0, 0));
        classDetail1.setFinishAt(LocalTime.of(10, 0, 0));
        classDetail1.setStatus("ACTIVE");
        classDetailRepository.save(classDetail1);


        classDetail2 = new ClassDetail();
        classDetail2.setClassField(classFields);
        classDetail2.setAccepted(20);
        classDetail2.setPlanned(20);
        classDetail2.setActual(20);
        classDetail2.setStartAt(LocalTime.of(8, 0, 0));
        classDetail2.setFinishAt(LocalTime.of(10, 0, 0));
        classDetail2.setStatus("INACTIVE");
        classDetailRepository.save(classDetail2);

        //User class detail

        ClassDetail classDetail = classDetailRepository.findById(classDetail1.getId()).orElse(null);
        List<User> users = userRepository.findAll();
        for (int i = 0; i < 4; i++) {
            User tmp = users.get(i);
            classDetail.getUsers().add(tmp);
        }
        classDetail1 = classDetailRepository.save(classDetail);

        classSchedule1 = new ClassSchedule();
        classSchedule1.setClassDetail(classDetailRepository.findById(classDetail1.getId()).orElse(null));
        classSchedule1.setTrainer(user2);
        classSchedule1.setStudyDate(LocalDate.of(2023, 3, 14));
        classScheduleRepository.save(classSchedule1);

        classSchedule2 = new ClassSchedule();
        classSchedule2.setClassDetail(classDetailRepository.findById(classDetail1.getId()).orElse(null));
        classSchedule2.setTrainer(user2);
        classSchedule2.setStudyDate(LocalDate.of(2023, 3, 17));
        classScheduleRepository.save(classSchedule2);

        classSchedule3 = new ClassSchedule();
        classSchedule3.setClassDetail(classDetailRepository.findById(classDetail1.getId()).orElse(null));
        classSchedule3.setTrainer(user2);
        classSchedule3.setStudyDate(LocalDate.of(2023, 3, 21));
        classScheduleRepository.save(classSchedule3);

        classSchedule4 = new ClassSchedule();
        classSchedule4.setClassDetail(classDetailRepository.findById(classDetail1.getId()).orElse(null));
        classSchedule4.setTrainer(user2);
        classSchedule4.setStudyDate(LocalDate.of(2023, 3, 24));
        classScheduleRepository.save(classSchedule4);

        classSchedule5 = new ClassSchedule();
        classSchedule5.setClassDetail(classDetailRepository.findById(classDetail2.getId()).orElse(null));
        classSchedule5.setTrainer(user2);
        classSchedule5.setStudyDate(LocalDate.of(2023, 3, 18));
        classScheduleRepository.save(classSchedule5);
    }

    @Test
    @DisplayName("Test findAdminsOfClass case 1")
    void findAdminsOfClassShouldReturnAListOfClassAdmin() {
        List<User> admins = userRepository.findAdminsOfClass(classDetail1.getId());
        assertNotNull(admins);
        assertEquals(1, admins.size());
        assertEquals("Class Admin", admins.get(0).getRole().getName());
    }

    @Test
    @DisplayName("Test findAdminsOfClass case 2")
    void findAdminsOfClassShouldReturnAnEmptyList() {
        List<User> admins = userRepository.findAdminsOfClass(classDetail2.getId());
        assertNotNull(admins);
        assertEquals(0, admins.size());
    }
}