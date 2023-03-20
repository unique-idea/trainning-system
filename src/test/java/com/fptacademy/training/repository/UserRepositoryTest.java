package com.fptacademy.training.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.domain.enumeration.UserStatus;
import com.fptacademy.training.security.Permissions;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private LevelRepository levelRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    private Direction direction = Direction.DESC;
    private String sortBy = "role";
    private final Pageable pageable = PageRequest.of(0, 2, direction, sortBy);
    
    private class UserIdComparator implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            return u1.getId().compareTo(u2.getId());
        }
    }

    class UserCustomComparator implements Comparator<User> {
        @Override
        public int compare(User u1, User u2) {
            switch (sortBy) {
                case "id":
                    return u1.getId().compareTo(u2.getId());
                case "code":
                    return u1.getCode().compareTo(u2.getCode());
                case "fullName":    
                    return u1.getFullName().compareTo(u2.getFullName());
                case "email":
                    return u1.getEmail().compareTo(u2.getEmail());
                case "birthday":
                    return u1.getBirthday().compareTo(u2.getBirthday());
                case "level":   
                    return u1.getLevel().getName().compareTo(u2.getLevel().getName());
                case "role":    
                    return u1.getRole().getName().compareTo(u2.getRole().getName());
                case "activated":   
                    return u1.getActivated().compareTo(u2.getActivated());
                case "status":  
                    return u1.getStatus().compareTo(u2.getStatus());
                default:    
                    return u1.getId().compareTo(u2.getId());
            }
        }
    }
    
    @BeforeEach
    void createFakeData() {
        Role roleUser = roleRepository.save(
                Role.builder()
                        .name("ROLE_USER")
                        .permissions(List.of(Permissions.USER_FULL_ACCESS))
                        .build());
        Role roleAdmin = roleRepository.save(
                Role.builder()
                        .name("ROLE_ADMIN")
                        .permissions(List.of(Permissions.USER_FULL_ACCESS))
                        .build());

        Level beginner = levelRepository.save(Level.builder().name("Beginner").build());
        Level intermediate = levelRepository.save(Level.builder().name("Intermediate").build());
        Level advanced = levelRepository.save(Level.builder().name("Advanced").build());

        User user1 = User.builder()
                .code("USER001")
                .fullName("Nguyen Van A")
                .email("a@gmail.com")
                .password("123456")
                .birthday(LocalDate.of(2001, 1, 1))
                .level(beginner)
                .role(roleUser)
                .activated(true)
                .status(UserStatus.IN_CLASS)
                .build();

        User user2 = User.builder()
                .code("USER002")
                .fullName("Nguyen Van B")
                .email("b@gmail.com")
                .password("123456")
                .birthday(LocalDate.of(1999, 1, 1))
                .level(intermediate)
                .role(roleAdmin)
                .activated(true)
                .status(UserStatus.ACTIVE)
                .build();

        User user3 = User.builder()
                .code("USER003")
                .fullName("Nguyen Van C")
                .email("c@gmail.com")
                .password("123456")
                .birthday(LocalDate.of(1997, 1, 1))
                .level(advanced)
                .role(roleUser)
                .activated(true)
                .status(UserStatus.OFF_CLASS)
                .build();

        userRepository.saveAll(List.of(user1, user2, user3));
    }

    @AfterEach
    void deleteFakeData() {
        userRepository.deleteAll();
        levelRepository.deleteAll();
        roleRepository.deleteAll();
    }

    private Boolean isUserMatchFilters(User user, String email, String fullName, String code, String levelName,
            String roleName, Boolean activated, LocalDate birthdayFrom, LocalDate birthdayTo, String status) {
        if (email != null && !user.getEmail().toLowerCase().contains(email.toLowerCase()))
            return false;
        if (fullName != null && !user.getFullName().toLowerCase().contains(fullName.toLowerCase())) 
            return false;
        if (code != null && !user.getCode().toLowerCase().contains(code.toLowerCase()))
            return false;
        if (levelName != null && !user.getLevel().getName().toLowerCase().contains(levelName.toLowerCase()))
            return false;
        if (roleName != null && !user.getRole().getName().toLowerCase().contains(roleName.toLowerCase()))
            return false;
        if (activated != null && !user.getActivated().equals(activated))
            return false;
        if (birthdayFrom == null) birthdayFrom = LocalDate.of(0, 1, 1);
        if (birthdayTo == null) birthdayTo = LocalDate.of(9999, 12, 31);
        if (user.getBirthday().isBefore(birthdayFrom) || user.getBirthday().isAfter(birthdayTo))
            return false;
        if (status != null && !user.getStatus().name().toLowerCase().contains(status.toLowerCase()))
            return false;
        return true;
    }

    @Test 
    void shouldReturnAllUsers_whenFindByNoFilters(){
        Page<User> allUsers = userRepository.findAll(pageable); 

        Page<User> usersNoFilters = userRepository.findByFilters(null, null, null, null, null, null, LocalDate.of(0, 1, 1), LocalDate.of(9999, 12, 31), null, pageable);
        assertThat(usersNoFilters.getSize()).isEqualTo(pageable.getPageSize());
        assertThat(usersNoFilters.getContent())
            .usingElementComparator(new UserIdComparator())
            .containsExactlyElementsOf(allUsers.getContent());
    }

    @Test 
    void shouldReturnCorrectUsers_whenFindByAllFilters(){
        final String email = "Gmail", fullName = "NGUYEN", code = "us", levelName = "BEGIN", roleName = "User", status = "_cl";
        final Boolean activated = true;
        final LocalDate birthdayFrom = LocalDate.of(2001, 1, 1); 
        final LocalDate birthdayTo = LocalDate.of(2001, 12, 31); 

        List<User> allUsers = userRepository.findAll();
        List<User> actualResult = allUsers.stream()
            .filter(user -> isUserMatchFilters(user, email, fullName, code, levelName, roleName, activated, birthdayFrom, birthdayTo, status))
            .toList();
        if (direction.isAscending())
            actualResult = actualResult.stream().sorted(new UserIdComparator()).toList();
        else 
            actualResult = actualResult.stream().sorted(new UserIdComparator().reversed()).toList();

        actualResult = actualResult.stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList();
    
        Page<User> usersFullFilters = userRepository.findByFilters(email, fullName, code, levelName, roleName, activated, birthdayFrom, birthdayTo, status, pageable);
        assertThat(usersFullFilters)
            .usingElementComparator(new UserIdComparator())
            .containsExactlyInAnyOrderElementsOf(actualResult);
    }

    @Test
    void shouldReturnCorrectUsers_whenFindByPartialFilters(){
        final String email = "Gmail", fullName = null, code = null, levelName = null, roleName = "User", status = null;
        final Boolean activated = true;
        final LocalDate birthdayFrom = null, birthdayTo = null; 

        List<User> allUsers = userRepository.findAll();
        List<User> actualResult = allUsers.stream()
            .filter(user -> isUserMatchFilters(user, email, fullName, code, levelName, roleName, activated, birthdayFrom, birthdayTo, status))
            .toList();
        if (direction.isAscending())
            actualResult = actualResult.stream().sorted(new UserIdComparator()).toList();
        else 
            actualResult = actualResult.stream().sorted(new UserIdComparator().reversed()).toList();
            
        actualResult = actualResult.stream().skip(pageable.getOffset()).limit(pageable.getPageSize()).toList();
    
        Page<User> usersFullFilters = userRepository.findByFilters(email, fullName, code, levelName, roleName, activated, birthdayFrom, birthdayTo, status, pageable);
        assertThat(usersFullFilters)
            .usingElementComparator(new UserIdComparator())
            .containsExactlyInAnyOrderElementsOf(actualResult);
    }
}
