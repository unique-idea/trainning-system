package com.fptacademy.training.repository;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fptacademy.training.domain.Level;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;

@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private LevelRepository levelRepository;

    @Autowired
    private RoleRepository roleRepository;

    private User user1, user2;
    private Level beginner, intermediate, advanced;
    private Role role_user, role_admin;

    void createFakeData() {
        role_user = roleRepository.save(Role.builder().name("ROLE_USER").build());
        role_admin = roleRepository.save(Role.builder().name("ROLE_ADMIN").build());

        beginner = levelRepository.save(Level.builder().name("Beginner").build());
        intermediate = levelRepository.save(Level.builder().name("Intermediate").build());
        advanced = levelRepository.save(Level.builder().name("Advanced").build());

        user1 = User.builder()
                .code("USER001")
                .fullName("Nguyen Van A")
                .email("a@gmail.com")
                .password("123456")
                .birthday(LocalDate)
    }

    @Test
    void testFindByFilters() {

    }
}
