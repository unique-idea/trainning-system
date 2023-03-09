package com.fptacademy.training.service;

import com.fptacademy.training.domain.User;
import com.fptacademy.training.exception.ResourceNotFoundException;
import com.fptacademy.training.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getCurrentUserLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Something went wrong, can not get current logged in user"));
    }

    public User getUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    public Collection<? extends GrantedAuthority> getUserPermissionsByEmail(String email) {
        return getUserByEmail(email)
                .getRole()
                .getPermissions()
                .stream().map(SimpleGrantedAuthority::new).toList();
    }
}
