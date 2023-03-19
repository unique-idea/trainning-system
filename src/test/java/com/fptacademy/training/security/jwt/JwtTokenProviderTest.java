package com.fptacademy.training.security.jwt;

import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import com.fptacademy.training.repository.RoleRepository;
import com.fptacademy.training.repository.UserRepository;
import com.fptacademy.training.security.Permissions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTokenProviderTest {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    private final long ONE_MINUTE = 1;
    final String EMAIL = "test@gmail.com";
    final String NAME = "Just A Test";
    final String CODE = "TEST123";
    final String AVATAR_URL = "https://image.com/test.jpg";
    final String PASSWORD = "test_password";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(tokenProvider, "accessExpireTimeInMinutes", ONE_MINUTE);
        ReflectionTestUtils.setField(tokenProvider, "refreshExpireTimeInMinutes", ONE_MINUTE);
    }

    private Authentication createAuthentication() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Permissions.PROGRAM_VIEW));
        return new UsernamePasswordAuthenticationToken(EMAIL, PASSWORD, authorities);
    }

    @Test
    void testAccessTokenReturnTrueWhenValid() {
        Authentication authentication = createAuthentication();
        String token = tokenProvider.generateAccessToken(authentication);

        boolean isTokenValid = tokenProvider.validateAccessToken(token);

        assertThat(isTokenValid).isTrue();
    }

    @Test
    void testAccessTokenReturnFalseWhenJWTisInvalid() {
        boolean isTokenValid = tokenProvider.validateAccessToken("");

        assertThat(isTokenValid).isFalse();
    }

    @Test
    void testAccessTokenReturnFalseWhenJWTisExpired() {
        ReflectionTestUtils.setField(tokenProvider, "accessExpireTimeInMinutes", -ONE_MINUTE);

        Authentication authentication = createAuthentication();
        String token = tokenProvider.generateAccessToken(authentication);

        boolean isTokenValid = tokenProvider.validateAccessToken(token);

        assertThat(isTokenValid).isFalse();
    }

    @Test
    @Transactional
    void testRefreshTokenReturnTrueWhenValid() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        Role role = new Role();
        role.setName("Test");
        role.setPermissions(List.of(Permissions.CLASS_CREATE));
        roleRepository.saveAndFlush(role);

        User user = new User();
        user.setEmail(EMAIL);
        user.setFullName(NAME);
        user.setCode(CODE);
        user.setAvatarUrl(AVATAR_URL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setActivated(true);
        user.setRole(role);
        userRepository.saveAndFlush(user);

        String token = tokenProvider.generateRefreshToken(EMAIL);

        boolean isTokenValid = tokenProvider.validateRefreshToken(token);

        assertThat(isTokenValid).isTrue();
    }

    @Test
    void testRefreshTokenReturnFalseWhenJWTisInvalid() {
        boolean isTokenValid = tokenProvider.validateAccessToken("");

        assertThat(isTokenValid).isFalse();
    }

    @Test
    @Transactional
    void testRefreshTokenReturnFalseWhenJWTisExpired() {
        ReflectionTestUtils.setField(tokenProvider, "refreshExpireTimeInMinutes", -ONE_MINUTE);

        userRepository.deleteAll();
        roleRepository.deleteAll();
        Role role = new Role();
        role.setName("Test");
        role.setPermissions(List.of(Permissions.CLASS_CREATE));
        roleRepository.saveAndFlush(role);

        User user = new User();
        user.setEmail(EMAIL);
        user.setFullName(NAME);
        user.setCode(CODE);
        user.setAvatarUrl(AVATAR_URL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setActivated(true);
        user.setRole(role);
        userRepository.saveAndFlush(user);

        String token = tokenProvider.generateRefreshToken(EMAIL);

        boolean isTokenValid = tokenProvider.validateRefreshToken(token);

        assertThat(isTokenValid).isFalse();
    }

    @Test
    void testGetAuthenticationFromAccessToken() {
        Authentication auth1 = createAuthentication();
        String token = tokenProvider.generateAccessToken(auth1);
        Authentication auth2 = tokenProvider.getAuthentication(token);
        assertThat(auth1.getName()).isEqualTo(auth2.getName());
        assertThat(auth1.getAuthorities()).isEqualTo(auth2.getAuthorities());
    }

    @Test
    @Transactional
    void testGenerateAccessTokenFromRefreshToken() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        Role role = new Role();
        role.setName("Test");
        role.setPermissions(List.of(Permissions.CLASS_CREATE));
        roleRepository.saveAndFlush(role);

        User user = new User();
        user.setEmail(EMAIL);
        user.setFullName(NAME);
        user.setCode(CODE);
        user.setAvatarUrl(AVATAR_URL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setActivated(true);
        user.setRole(role);
        userRepository.saveAndFlush(user);
        String refreshToken = tokenProvider.generateRefreshToken(EMAIL);
        String accessToken = tokenProvider.generateAccessToken(refreshToken);
        boolean isValidToken = tokenProvider.validateAccessToken(accessToken);
        assertThat(isValidToken).isTrue();
    }
}