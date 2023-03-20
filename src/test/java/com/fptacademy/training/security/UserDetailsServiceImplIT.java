package com.fptacademy.training.security;

import static org.assertj.core.api.Assertions.*;

import com.fptacademy.training.IntegrationTest;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.web.TestUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@IntegrationTest
class UserDetailsServiceImplIT {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsService userDetailsService;
    private User user;
    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        Role role = TestUtil.getRole(List.of(Permissions.CLASS_CREATE));
        roleRepository.saveAndFlush(role);
        user = TestUtil.getUser(role);
        userRepository.saveAndFlush(user);
    }

    @AfterEach
    void teardown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testFindUserByEmail() {
        UserDetails userDetails = userDetailsService.loadUserByUsername(TestUtil.EMAIL);
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(TestUtil.EMAIL);
    }

    @Test
    void testFindUserByEmailFails() {
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userDetailsService.loadUserByUsername("wrong-email"));
    }
}