package com.fptacademy.training.service;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.factory.RoleFactory;
import com.fptacademy.training.factory.UserFactory;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.security.Permissions;
import com.fptacademy.training.web.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setup() {
        Role role = RoleFactory.createRoleWithPermissions(Permissions.PROGRAM_FULL_ACCESS);
        roleRepository.saveAndFlush(role);
        user = UserFactory.createActiveUser(role);
        userRepository.saveAndFlush(user);
    }
    @AfterEach
    void teardown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testGetCurrentLoggedInUser() {
        Authentication authentication = TestUtil.createAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User currentUser = userService.getCurrentUserLogin();
        assertThat(currentUser.getId()).isEqualTo(user.getId());
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetCurrentLoggedInUserFails() {
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userService.getCurrentUserLogin())
                .withMessage("Something went wrong, can not get current logged in user");
    }

    @Test
    void testGetUserByEmail() {
        User result = userService.getUserByEmail(user.getEmail());
        assertThat(result.getId()).isEqualTo(user.getId());
    }

    @Test
    void testGetUserByEmailFails() {
        String email = "invalid-email";
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> userService.getUserByEmail(email))
                .withMessage("User with email " + email + " not found");
    }

    @Test
    void testGetUserPermissionsByEmail() {
        Collection<? extends GrantedAuthority> authorities = userService.getUserPermissionsByEmail(user.getEmail());
        assertThat(authorities.size()).isEqualTo(user.getRole().getPermissions().size());
        assertThat(authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(e1 -> user.getRole().getPermissions().stream()
                        .anyMatch(e1::equals))).isTrue();
    }
}