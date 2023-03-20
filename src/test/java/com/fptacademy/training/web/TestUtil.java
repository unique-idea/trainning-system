package com.fptacademy.training.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fptacademy.training.domain.Role;
import com.fptacademy.training.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class TestUtil {
    public static final String EMAIL = "test@gmail.com";
    public static final String PASSWORD = "test";
    public static final String CODE = "TEST123";
    public static final String FULL_NAME = "Test";
    public static final String AVATAR_URL = "https://image.com/test.jpg";
    private static final ObjectMapper mapper = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    public static Authentication createAuthentication(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>(user.getRole().getPermissions().stream().map(SimpleGrantedAuthority::new).toList());
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(), authorities);
    }

    public static Role getRole(List<String> permissions) {
        Role role = new Role();
        role.setName("Test Role");
        role.setPermissions(permissions);
        return role;
    }

    public static User getUser(Role role) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setCode(CODE);
        user.setFullName(FULL_NAME);
        user.setRole(role);
        user.setActivated(true);
        user.setAvatarUrl(AVATAR_URL);
        return user;
    }
}
